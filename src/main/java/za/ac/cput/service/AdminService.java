package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.*;
import za.ac.cput.repository.AdminRepository;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.RegularUserRepository;
import za.ac.cput.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {

    private final AdminRepository adminRepository;
    private final RegularUserRepository regularUserRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository, RegularUserRepository regularUserRepository,
                        CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.adminRepository = adminRepository;
        this.regularUserRepository = regularUserRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Admin create(Admin admin) {
        // Grant all permissions to every admin by default
        List<Permission> allPermissions = Arrays.asList(Permission.values());

        Admin newAdmin = new Admin.AdminBuilder()
                .setUserName(admin.getUserName())
                .setEmail(admin.getEmail())
                .setPassword(admin.getPassword())
                .setAdminCode(admin.getAdminCode())
                .setPermissions(allPermissions)
                .build();

        return adminRepository.save(newAdmin);
    }

    @Override
    public Admin logIn(String email, String password) {
        List<Admin> foundAdmins = adminRepository.findByEmail(email);
        if (foundAdmins.isEmpty()) {
            // Try to find by email if not found by username
            foundAdmins = adminRepository.findByPassword(password);
        }

        if (!foundAdmins.isEmpty()) {
            Admin admin = foundAdmins.get(0);
            if (admin.getPassword().equals(password)) {
                return admin; // Login successful
            }
        }

        return null;
    }

    @Override
    public Admin read(Long id) {
        return adminRepository.findById(id).orElse(null);
    }

    @Override
    public Admin update(Admin admin) {
        return adminRepository.findById(admin.getUserID())
                .map(existingAdmin -> {
                    // Keep existing permissions intact
                    Admin updatedAdmin = new Admin.AdminBuilder()
                            .copy(existingAdmin)
                            .setUserName(admin.getUserName())
                            .setEmail(admin.getEmail())
                            .setPassword(admin.getPassword())
                            .setAdminCode(admin.getAdminCode())
                            .build();

                    return adminRepository.save(updatedAdmin);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public List<Admin> findByUserName(String userName) {
        return adminRepository.findByUserName(userName);
    }

    @Override
    public List<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    @Override
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    public List<RegularUser> viewAllRegularUsers() {
        return regularUserRepository.findAll();
    }

    @Override
    public void deleteRegularUserById(Long id) {
        regularUserRepository.deleteById(id);
    }

    @Override
    public void updateRegularUserById(Long id, RegularUser updatedUser) {
        regularUserRepository.findById(id)
                .map(existingUser -> {
                    // Copy existing user and update fields
                    RegularUser newUser = new RegularUser.RegularUserBuilder()
                            .copy(existingUser)
                            .setUserName(updatedUser.getUserName())
                            .setEmail(updatedUser.getEmail())
                            .setPassword(updatedUser.getPassword())
                            .build();
                    return regularUserRepository.save(newUser);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public Category createCategory(String name, String type) {
        Category category = new Category.CategoryBuilder()
                .setName(name)
                .setType(type)
                .build();
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id, String name, String type) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    // Update the existing category with new values
                    Category updatedCategory = new Category.CategoryBuilder()
                            .copy(existingCategory)
                            .setName(name)
                            .setType(type)
                            .build();
                    return categoryRepository.save(updatedCategory);
                })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category deleteCategory(Long id) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    categoryRepository.delete(existingCategory);
                    return existingCategory; // Return the deleted category
                })
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public List viewAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Map<String, Object> viewAnonymizedAnalytics() {
        List<Transaction> transactions = transactionRepository.findAll();

        long totalTransactions = transactions.size();
        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        double averageAmount = totalTransactions > 0 ? totalAmount / totalTransactions : 0;

        Map<String, Long> transactionsByCategory = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getCategory().getName(), Collectors.counting()));

        Map<String, Long> transactionsByType = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()));

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("totalTransactions", totalTransactions);
        analytics.put("totalAmount", totalAmount);
        analytics.put("averageAmount", averageAmount);
        analytics.put("transactionsByCategory", transactionsByCategory);
        analytics.put("transactionsByType", transactionsByType);

        return analytics;
    }


    @Override
    public Map<String, Object> viewAnonymizedAnalyticsByCategory(String categoryName) {
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .filter(t -> t.getCategory() != null &&
                        t.getCategory().getName().equalsIgnoreCase(categoryName))
                .collect(Collectors.toList());

        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        long count = transactions.size();
        double averageAmount = count > 0 ? totalAmount / count : 0;

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("category", categoryName);
        analytics.put("totalTransactions", count);
        analytics.put("totalAmount", totalAmount);
        analytics.put("averageAmount", averageAmount);

        return analytics;
    }


     //View anonymized analytics between two dates (inclusive).
    @Override
    public Map<String, Object> viewAnonymizedAnalyticsByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Transaction> transactions = transactionRepository.findByDateBetween(start, end);

        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        long count = transactions.size();
        double averageAmount = count > 0 ? totalAmount / count : 0;

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("dateRange", start + " to " + end);
        analytics.put("totalTransactions", count);
        analytics.put("totalAmount", totalAmount);
        analytics.put("averageAmount", averageAmount);

        return analytics;
    }


     // View anonymized analytics for a specific transaction type.
    @Override
    public Map<String, Object> viewAnonymizedAnalyticsByTransactionType(String transactionType) {
        List<Transaction> transactions = transactionRepository.findByType(transactionType);

        Map<String, Long> countByCategory = transactions.stream()
                .collect(Collectors.groupingBy(t -> t.getCategory().getName(), Collectors.counting()));

        double totalAmount = transactions.stream().mapToDouble(Transaction::getAmount).sum();
        long count = transactions.size();
        double averageAmount = count > 0 ? totalAmount / count : 0;

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("transactionType", transactionType);
        analytics.put("totalTransactions", count);
        analytics.put("totalAmount", totalAmount);
        analytics.put("averageAmount", averageAmount);
        analytics.put("countByCategory", countByCategory);

        return analytics;
    }


}
