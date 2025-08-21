package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Admin;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.service.AdminService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    public static class LoginRequest {
        public String usernameOrEmail;
        public String password;
    }


    // Admin CRUD
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


    // Admin Login (JSON Body)
    @PostMapping("/login")
    public ResponseEntity<Admin> logIn(@RequestBody LoginRequest request) {
        Admin admin = adminService.logIn(request.usernameOrEmail, request.password);
        return (admin != null) ? ResponseEntity.ok(admin) : ResponseEntity.status(401).build();
    }


    // Regular User Management
    @GetMapping("/regular-users")
    public ResponseEntity<List<RegularUser>> viewAllRegularUsers() {
        List<RegularUser> users = adminService.viewAllRegularUsers();
        return (!users.isEmpty()) ? ResponseEntity.ok(users) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/regular-users/delete/{id}")
    public ResponseEntity<Void> deleteRegularUserById(@PathVariable Long id) {
        adminService.deleteRegularUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/regular-users/update/{id}")
    public ResponseEntity<RegularUser> updateRegularUserById(
            @PathVariable Long id, @RequestBody RegularUser updatedUser) {
        try {
            adminService.updateRegularUserById(id, updatedUser);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Category Management
    @PostMapping("/categories/create")
    public ResponseEntity<Category> createCategory(@RequestParam String name,
                                                   @RequestParam String type) {
        Category createdCategory = adminService.createCategory(name, type);
        return ResponseEntity.ok(createdCategory);
    }

    @PutMapping("/categories/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id,
                                                   @RequestParam String name,
                                                   @RequestParam String type) {
        try {
            Category updatedCategory = adminService.updateCategory(id, name, type);
            return ResponseEntity.ok(updatedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/categories/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        try {
            Category deletedCategory = adminService.deleteCategory(id);
            return ResponseEntity.ok(deletedCategory);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/categories/all")
    public ResponseEntity<List<Category>> viewAllCategories() {
        List<Category> categories = adminService.viewAllCategories();
        return (!categories.isEmpty()) ? ResponseEntity.ok(categories) : ResponseEntity.notFound().build();
    }


    //Analytics
    @GetMapping("/analytics")
    public ResponseEntity<Void> viewAnonymizedAnalytics() {
        adminService.viewAnonymizedAnalytics();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analytics/category/{category}")
    public ResponseEntity<Map<String, Object>> viewAnonymizedAnalyticsByCategory(@PathVariable String category) {
        Map<String, Object> data = adminService.viewAnonymizedAnalyticsByCategory(category);
        return (data != null && !data.isEmpty()) ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();
    }

    @GetMapping("/analytics/date-range")
    public ResponseEntity<Void> viewAnonymizedAnalyticsByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        adminService.viewAnonymizedAnalyticsByDateRange(startDate, endDate);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/analytics/transaction-type/{transactionType}")
    public ResponseEntity<Void> viewAnonymizedAnalyticsByTransactionType(@PathVariable String transactionType) {
        adminService.viewAnonymizedAnalyticsByTransactionType(transactionType);
        return ResponseEntity.ok().build();
    }
}
