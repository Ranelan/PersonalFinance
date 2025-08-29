package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.User;
import za.ac.cput.service.impl.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // --- DTO for Login Request ---
    public static class LoginRequest {
        public String usernameOrEmail;
        public String password;
    }

    // A utility to hide the password before sending the user object back to the client.
    private User hidePassword(User user) {
        if (user != null) {
            // Use the builder's copy method to create a new object without the password
            return new User.UserBuilder().copy(user).setPassword(null).build();
        }
        return null;
    }

    // =================================================================
    // == PUBLIC USER ACTIONS (/api/users)
    // =================================================================

    @PostMapping("/users/create")
    public ResponseEntity<User> create(@RequestBody User user) {
        User createdUser = userService.create(user);
        return new ResponseEntity<>(hidePassword(createdUser), HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<User> logIn(@RequestBody LoginRequest request) {
        // The logIn service method already returns the user object with the password hidden.
        User user = userService.logIn(request.usernameOrEmail, request.password);
        return ResponseEntity.ok(user);
    }

    // =================================================================
    // == ADMIN-PROTECTED USER MANAGEMENT (/api/admin/users)
    // =================================================================

    @GetMapping("/admin/users/read/{id}")
    public ResponseEntity<User> read(@PathVariable Long id) {
        User user = userService.read(id);
        return ResponseEntity.ok(hidePassword(user));
    }

    @PutMapping("/admin/users/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(hidePassword(updatedUser));
    }

    @DeleteMapping("/admin/users/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/users/all")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll().stream()
                .map(this::hidePassword)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/admin/users/regular")
    public ResponseEntity<List<User>> viewAllRegularUsers() {
        List<User> regularUsers = userService.viewAllRegularUsers().stream()
                .map(this::hidePassword)
                .collect(Collectors.toList());
        return ResponseEntity.ok(regularUsers);
    }

    // =================================================================
    // == ADMIN-PROTECTED CATEGORY MANAGEMENT (/api/admin/categories)
    // =================================================================

    @PostMapping("/admin/categories/create")
    public ResponseEntity<Category> createCategory(@RequestParam String name, @RequestParam String type) {
        Category newCategory = userService.createCategory(name, type);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/update/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestParam String name, @RequestParam String type) {
        Category updatedCategory = userService.updateCategory(id, name, type);
        return ResponseEntity.ok(updatedCategory);
    }

    @DeleteMapping("/admin/categories/delete/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        Category deletedCategory = userService.deleteCategory(id);
        return ResponseEntity.ok(deletedCategory);
    }

    @GetMapping("/admin/categories/all")
    public ResponseEntity<List<Category>> viewAllCategories() {
        List<Category> categories = userService.viewAllCategories();
        return ResponseEntity.ok(categories);
    }

    // =================================================================
    // == ADMIN-PROTECTED ANALYTICS (/api/admin/analytics)
    // =================================================================

    @GetMapping("/admin/analytics/general")
    public ResponseEntity<Map<String, Object>> getGeneralAnalytics() {
        Map<String, Object> analytics = userService.viewAnonymizedAnalytics();
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/admin/analytics/by-category")
    public ResponseEntity<Map<String, Object>> getAnalyticsByCategory(@RequestParam String categoryName) {
        Map<String, Object> analytics = userService.viewAnonymizedAnalyticsByCategory(categoryName);
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/admin/analytics/by-date-range")
    public ResponseEntity<Map<String, Object>> getAnalyticsByDateRange(@RequestParam String startDate, @RequestParam String endDate) {
        Map<String, Object> analytics = userService.viewAnonymizedAnalyticsByDateRange(startDate, endDate);
        return ResponseEntity.ok(analytics);
    }

    @GetMapping("/admin/analytics/by-type")
    public ResponseEntity<Map<String, Object>> getAnalyticsByType(@RequestParam String transactionType) {
        Map<String, Object> analytics = userService.viewAnonymizedAnalyticsByTransactionType(transactionType);
        return ResponseEntity.ok(analytics);
    }
}