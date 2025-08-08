package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.service.AdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // -------------------------
    // Admin CRUD
    // -------------------------
    @PostMapping("/create")
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.create(admin);
        return (createdAdmin != null) ? ResponseEntity.ok(createdAdmin) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Admin> read(@PathVariable Long id) {
        Admin admin = adminService.read(id);
        return (admin != null) ? ResponseEntity.ok(admin) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.update(admin);
        return (updatedAdmin != null) ? ResponseEntity.ok(updatedAdmin) : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByUserName/{userName}")
    public ResponseEntity<List<Admin>> findByUserName(@PathVariable String userName) {
        List<Admin> admins = adminService.findByUserName(userName);
        return (!admins.isEmpty()) ? ResponseEntity.ok(admins) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<List<Admin>> findByEmail(@PathVariable String email) {
        List<Admin> admins = adminService.findByEmail(email);
        return (!admins.isEmpty()) ? ResponseEntity.ok(admins) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Admin>> findAll() {
        List<Admin> admins = adminService.findAll();
        return (!admins.isEmpty()) ? ResponseEntity.ok(admins) : ResponseEntity.notFound().build();
    }

    // -------------------------
    // Admin Login (JSON Body)
    // -------------------------
    public static class LoginRequest {
        public String usernameOrEmail;
        public String password;
    }

    @PostMapping("/login")
    public ResponseEntity<Admin> logIn(@RequestBody LoginRequest request) {
        Admin admin = adminService.logIn(request.usernameOrEmail, request.password);
        return (admin != null) ? ResponseEntity.ok(admin) : ResponseEntity.status(401).build();
    }

    // -------------------------
    // Regular User Management
    // -------------------------
    @GetMapping("/regular-users")
    public ResponseEntity<List<RegularUser>> viewAllRegularUsers() {
        List<RegularUser> users = adminService.viewAllRegularUsers();
        return (!users.isEmpty()) ? ResponseEntity.ok(users) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/regular-users/{id}")
    public ResponseEntity<Void> deleteRegularUserById(@PathVariable Long id) {
        adminService.deleteRegularUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/regular-users/{id}")
    public ResponseEntity<RegularUser> updateRegularUserById(
            @PathVariable Long id, @RequestBody RegularUser updatedUser) {
        try {
            adminService.updateRegularUserById(id, updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
