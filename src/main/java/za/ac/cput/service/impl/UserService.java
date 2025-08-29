package za.ac.cput.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize; // <-- The required import
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.*;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.TransactionRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.RoleRepository;
import za.ac.cput.service.IUserService;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final TransactionRepository transactionRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, CategoryRepository categoryRepository, TransactionRepository transactionRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User logIn(String usernameOrEmail, String password) {
        Optional<User> userOptional = userRepository.findByUserNameOrEmail(usernameOrEmail, usernameOrEmail);
        User user = userOptional.orElseThrow(() -> new EntityNotFoundException("User not found: " + usernameOrEmail));

        // Note: Plain text password comparison is not secure.
        if (user.getPassword().equals(password)) {
            // Hide password before returning
            return new User.UserBuilder().copy(user).setPassword(null).build();
        } else {
            throw new IllegalArgumentException("Invalid password provided");
        }
    }


    @Override
    public User create(User userFromRequest) {
        if (userRepository.existsByUserName(userFromRequest.getUserName())) {
            throw new IllegalArgumentException("Username is already taken!");
        }
        if (userRepository.existsByEmail(userFromRequest.getEmail())) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isRequestFromAdmin = authentication != null &&
                authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));

        Role finalRole;
        Set<Permission> finalPermissions = new HashSet<>();

        if (isRequestFromAdmin) {
            // Admin is creating a user. Trust the role from the request if it exists, otherwise default to REGULAR_USER.
            if (userFromRequest.getRole() != null && userFromRequest.getRole().getName() != null) {
                finalRole = roleRepository.findByName(userFromRequest.getRole().getName())
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: " + userFromRequest.getRole().getName()));
            } else {
                finalRole = roleRepository.findByName("REGULAR_USER")
                        .orElseThrow(() -> new IllegalArgumentException("Role not found: REGULAR_USER"));
            }
        } else {
            // An anonymous user is registering. IGNORE the role in the request.
            finalRole = roleRepository.findByName("REGULAR_USER")
                    .orElseThrow(() -> new IllegalArgumentException("Role not found: REGULAR_USER"));
        }

        // Assign permissions based on the *securely determined* role
        if (finalRole.getName().equals("ADMIN")) {
            finalPermissions = new HashSet<>(Arrays.asList(Permission.values()));
        }
        // Regular users get an empty set of permissions by default.

        User userToSave = new User.UserBuilder()
                .copy(userFromRequest)
                .setRole(finalRole)
                .setPermissions(finalPermissions)
                .build();

        return userRepository.save(userToSave);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public User read(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public User update(User userWithNewData) {
        // 1. Get the full, existing user from the database.
        User existingUser = read(userWithNewData.getUserID());

        // 2. Use the builder to create an updated copy.
        // This preserves ID, password, role, permissions, etc.
        User updatedUser = new User.UserBuilder()
                .copy(existingUser) // Start with the original user's data
                .setUserName(userWithNewData.getUserName()) // Overwrite only the changed fields
                .setEmail(userWithNewData.getEmail())
                .build();

        return userRepository.save(updatedUser);
    }


    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> viewAllRegularUsers() {
        Role regularUserRole = roleRepository.findByName("REGULAR_USER")
                .orElseThrow(() -> new EntityNotFoundException("Role not found: REGULAR_USER"));
        return userRepository.findByRole(regularUserRole);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteRegularUserById(Long id) {
        User user = read(id);
        if (user.getRole() != null && "ADMIN".equals(user.getRole().getName())) {
            throw new IllegalArgumentException("Cannot delete an admin using this method.");
        }
        userRepository.deleteById(id);
    }

    @Override
    public void updateUserById(Long id, User user) {

    }

    // ... all other methods for Category and Analytics remain the same ...
    // --- Find Methods ---

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName)
                .orElseThrow(() -> new EntityNotFoundException("User not found with username: " + userName));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    // --- Category Management (Admin Only) ---

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Category createCategory(String name, String type) {
        Category category = new Category.CategoryBuilder()
                .setName(name)
                .setType(type)
                .build();
        return categoryRepository.save(category);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Category updateCategory(Long id, String name, String type) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    Category updatedCategory = new Category.CategoryBuilder()
                            .copy(existingCategory)
                            .setName(name)
                            .setType(type)
                            .build();
                    return categoryRepository.save(updatedCategory);
                })
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Category deleteCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        categoryRepository.delete(category);
        return category;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<Category> viewAllCategories() {
        return categoryRepository.findAll();
    }


    // --- Analytics (Admin Only & Refactored for Graphs) ---

    /**
     * Provides general analytics data structured for multiple charts on a dashboard.
     * Ideal for generating a bar chart (by category) and a pie chart (by type).
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> viewAnonymizedAnalytics() {
        List<Transaction> transactions = transactionRepository.findAll();

        // Data for "Transactions by Category" Bar Chart
        Map<String, Long> byCategory = transactions.stream()
                .filter(t -> t.getCategory() != null)
                .collect(Collectors.groupingBy(t -> t.getCategory().getName(), Collectors.counting()));

        // Data for "Transactions by Type" Pie Chart
        Map<String, Long> byType = transactions.stream()
                .filter(t -> t.getType() != null && !t.getType().isEmpty())
                .collect(Collectors.groupingBy(Transaction::getType, Collectors.counting()));

        Map<String, Object> analytics = new HashMap<>();

        // Chart 1: Data for a bar chart
        analytics.put("transactionsByCategory", Map.of(
                "labels", byCategory.keySet(),
                "datasets", List.of(Map.of("label", "Count", "data", byCategory.values()))
        ));

        // Chart 2: Data for a pie chart
        analytics.put("transactionsByType", Map.of(
                "labels", byType.keySet(),
                "datasets", List.of(Map.of("data", byType.values()))
        ));

        return analytics;
    }

    /**
     * Provides summary KPIs for a specific category.
     * This is less of a chart and more of a "stats card" on a dashboard.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
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
        analytics.put("totalAmount", String.format("%.2f", totalAmount));
        analytics.put("averageAmount", String.format("%.2f", averageAmount));
        return analytics;
    }

    /**
     * Provides data structured for a time-series line or bar chart.
     * The X-axis will be dates, and the Y-axis will be the total amount.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> viewAnonymizedAnalyticsByDateRange(String startDate, String endDate) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        List<Transaction> transactions = transactionRepository.findByDateBetween(start, end);

        // Group transactions by date and sum their amounts for the chart
        Map<LocalDate, Double> dataByDate = transactions.stream()
                .collect(Collectors.groupingBy(
                        Transaction::getDate,
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        // Sort the data by date for a proper time-series graph
        Map<LocalDate, Double> sortedData = new TreeMap<>(dataByDate);

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("dateRange", start + " to " + end);
        analytics.put("chartData", Map.of(
                "labels", sortedData.keySet(),
                "datasets", List.of(Map.of("label", "Total Amount", "data", sortedData.values()))
        ));
        return analytics;
    }

    /**
     * Provides a breakdown by category for a specific transaction type (e.g., "Expense").
     * Ideal for a pie or doughnut chart.
     */
    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, Object> viewAnonymizedAnalyticsByTransactionType(String transactionType) {
        List<Transaction> transactions = transactionRepository.findByType(transactionType);

        Map<String, Double> amountByCategory = transactions.stream()
                .filter(t -> t.getCategory() != null)
                .collect(Collectors.groupingBy(
                        t -> t.getCategory().getName(),
                        Collectors.summingDouble(Transaction::getAmount)
                ));

        Map<String, Object> analytics = new HashMap<>();
        analytics.put("transactionType", transactionType);
        analytics.put("chartData", Map.of(
                "labels", amountByCategory.keySet(),
                "datasets", List.of(Map.of("data", amountByCategory.values()))
        ));
        return analytics;
    }
}

