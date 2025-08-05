package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Admin;
import za.ac.cput.service.IAdminService;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final IAdminService adminService;

    @Autowired
    public AdminController(IAdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/create")
    public ResponseEntity<Admin> create(@RequestBody Admin admin) {
        Admin createdAdmin = adminService.create(admin);
        if (createdAdmin != null) {
            return ResponseEntity.ok(createdAdmin);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Admin> read(@PathVariable Long id) {
        Admin admin = adminService.read(id);
        if (admin != null) {
            return ResponseEntity.ok(admin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Admin> update(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.update(admin);
        if (updatedAdmin != null) {
            return ResponseEntity.ok(updatedAdmin);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        adminService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByUserName/{userName}")
    public ResponseEntity<List<Admin>> findByUserName(@PathVariable String userName) {
        List<Admin> admins = adminService.findByUserName(userName);
        if (admins != null && !admins.isEmpty()) {
            return ResponseEntity.ok(admins);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByEmail/{email}")
    public ResponseEntity<List<Admin>> findByEmail(@PathVariable String email) {
        List<Admin> admins = adminService.findByEmail(email);
        if (admins != null && !admins.isEmpty()) {
            return ResponseEntity.ok(admins);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Admin>> findAll() {
        List<Admin> admins = adminService.findAll();
        if (admins != null && !admins.isEmpty()) {
            return ResponseEntity.ok(admins);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}