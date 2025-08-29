package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserFactoryTest {
    private String userName;
    private String email;
    private String password;
    private Role role;
    private List<Transaction> transactions;
    private List<RecurringTransaction> recurringTransactions;
    private List<Budget> budgets;
    private List<Goal> goals;

    @BeforeEach
    void setUp() {
        userName = "testuser";
        email = "testuser@email.com";
        password = "Password123!";
        role = new Role("REGULAR_USER");
        transactions = new ArrayList<>();
        recurringTransactions = new ArrayList<>();
        budgets = new ArrayList<>();
        goals = new ArrayList<>();
    }

    @Test
    void createUserSuccess() {
        User user = UserFactory.createUser(userName, email, password, role, transactions, recurringTransactions, budgets, goals);
        assertNotNull(user);
        assertEquals(userName, user.getUserName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role.getName(), user.getRole().getName());
        assertTrue(user.getPermissions().isEmpty(), "Regular user should have no permissions");
        assertEquals(transactions, user.getTransactions());
        assertEquals(recurringTransactions, user.getRecurringTransactions());
        assertEquals(budgets, user.getBudgets());
        assertEquals(goals, user.getGoals());
    }

    @Test
    void createUserAdminHasAllPermissions() {
        Role adminRole = new Role("ADMIN");
        User admin = UserFactory.createUser(userName, email, password, adminRole, transactions, recurringTransactions, budgets, goals);
        assertNotNull(admin);
        assertEquals(adminRole.getName(), admin.getRole().getName());
        assertEquals(Permission.values().length, admin.getPermissions().size());
    }

    @Test
    void createUserAdminSuccess() {
        Role adminRole = new Role("ADMIN");
        User admin = UserFactory.createUser(userName, email, password, adminRole, transactions, recurringTransactions, budgets, goals);
        assertNotNull(admin);
        assertEquals(adminRole.getName(), admin.getRole().getName());
        assertEquals(Permission.values().length, admin.getPermissions().size(), "Admin should have all permissions");
        for (Permission p : Permission.values()) {
            assertTrue(admin.getPermissions().contains(p), "Admin should have permission: " + p);
        }
        assertEquals(transactions, admin.getTransactions());
        assertEquals(recurringTransactions, admin.getRecurringTransactions());
        assertEquals(budgets, admin.getBudgets());
        assertEquals(goals, admin.getGoals());
    }

    @Test
    void createUserInvalidEmail() {
        User user = UserFactory.createUser(userName, "invalidemail", password, role, transactions, recurringTransactions, budgets, goals);
        assertNull(user);
    }

    @Test
    void createUserInvalidPassword() {
        User user = UserFactory.createUser(userName, email, "short", role, transactions, recurringTransactions, budgets, goals);
        assertNull(user);
    }

    @Test
    void createUserNullRole() {
        User user = UserFactory.createUser(userName, email, password, null, transactions, recurringTransactions, budgets, goals);
        assertNull(user);
    }

    @Test
    void createUserNullUserName() {
        User user = UserFactory.createUser(null, email, password, role, transactions, recurringTransactions, budgets, goals);
        assertNull(user);
    }
}
