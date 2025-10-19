package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.service.IRegularUserService;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/regularUser")
public class RegularUserController {

    private final IRegularUserService regularUserService;

    @Autowired
    public RegularUserController(IRegularUserService regularUserService) {
        this.regularUserService = regularUserService;
    }

    public static class LoginRequest {
        public String usernameOrEmail;
        public String password;
    }

    @PostMapping("/create")
    public ResponseEntity<RegularUser> create(@RequestBody RegularUser regularUser) {
        RegularUser createdUser = regularUserService.create(regularUser);
        if (createdUser != null) {
            return ResponseEntity.ok(createdUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<RegularUser> logIn(@RequestBody RegularUserController.LoginRequest request) {
        // Only support login by email for now
        RegularUser regularUser = regularUserService.logIn(request.usernameOrEmail, request.password);
        return (regularUser != null) ? ResponseEntity.ok(regularUser) : ResponseEntity.status(401).build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<RegularUser> read(@PathVariable Long id) {
        RegularUser user = regularUserService.read(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<RegularUser> update(@RequestBody RegularUser regularUser) {
        RegularUser updatedUser = regularUserService.update(regularUser);
        if (updatedUser != null) {
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regularUserService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByUserName/{userName}")
    public ResponseEntity<List<RegularUser>> findByUserName(@PathVariable String userName) {
        List<RegularUser> users = regularUserService.findByUserName(userName);
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<RegularUser> findByEmail(@PathVariable String email) {
        Optional<RegularUser> user = regularUserService.findByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<RegularUser>> findAll() {
        List<RegularUser> users = regularUserService.findAll();
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
