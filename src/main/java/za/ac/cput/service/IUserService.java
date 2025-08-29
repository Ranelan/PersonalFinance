package za.ac.cput.service;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.User;

import java.util.List;
import java.util.Map;

public interface IUserService extends IService <User, Long> {

    User logIn(String usernameOrEmail, String password);
    User read(Long id);
    List<User> findAll(); // Note: We will restrict this in the implementation
    User findByUserName(String userName);
    User findByEmail(String email);
    //User createAdmin(User admin); // For creating admin users


    // --- Admin-Specific Operations ---
    // User Management
    List<User> viewAllRegularUsers();
    void deleteRegularUserById(Long id);
    void updateUserById(Long id, User user); // Generic update, logic will be in implementation

    // Category Management
    Category createCategory(String name, String type);
    Category updateCategory(Long id, String name, String type);
    Category deleteCategory(Long id);
    List<Category> viewAllCategories();

    // Analytics
    Map<String, Object> viewAnonymizedAnalytics();
    Map<String, Object> viewAnonymizedAnalyticsByCategory(String category);
    Map<String, Object> viewAnonymizedAnalyticsByDateRange(String startDate, String endDate);
    Map<String, Object> viewAnonymizedAnalyticsByTransactionType(String transactionType);
}

