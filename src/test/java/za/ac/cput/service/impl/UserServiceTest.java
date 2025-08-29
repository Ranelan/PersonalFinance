package za.ac.cput.service.impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import za.ac.cput.domain.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    private List<Long> createdUserIds = new ArrayList<>();

    private User createTestUser(String username, String email, String password, Role role) {
        if (role != null && "ADMIN".equals(role.getName())) {
            setAdminAuthentication();
        }
        User user = new User.UserBuilder()
                .setUserName(username)
                .setEmail(email)
                .setPassword(password)
                .setRole(role)
                .setPermissions(new HashSet<>())
                .setTransactions(new ArrayList<>())
                .setRecurringTransactions(new ArrayList<>())
                .setBudgets(new ArrayList<>())
                .setGoals(new ArrayList<>())
                .build();
        User created = userService.create(user);
        createdUserIds.add(created.getUserID());
        return created;
    }

    private void setAdminAuthentication() {
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken("admin", "Password123!", authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    @AfterEach
    void tearDown() {
        for (Long id : createdUserIds) {
            try { userService.delete(id); } catch (Exception ignored) {}
        }
        createdUserIds.clear();
        SecurityContextHolder.clearContext();
    }

    @Test
    void logIn() {
        User user = createTestUser("KhuliJunior", "login2@email.com", "Password123!", new Role("REGULAR_USER"));
        User loggedIn = userService.logIn("loginUser", "Password123!");
        assertNotNull(loggedIn);
        assertEquals(user.getUserName(), loggedIn.getUserName());
        assertNull(loggedIn.getPassword());
        // Wrong password
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.logIn("loginUser", "WrongPass!"));
        assertTrue(ex.getMessage().contains("Invalid password"));
        // Non-existent user
        Exception ex2 = assertThrows(Exception.class, () -> userService.logIn("noUser", "Password123!"));
        assertTrue(ex2.getMessage().contains("not found"));
    }

    @Test
    void create() {
        User user = new User.UserBuilder()
                .setUserName("Khuliso")
                .setEmail("unique2@email.com")
                .setPassword("Password123!")
                .setRole(new Role("REGULAR_USER"))
                .setPermissions(new HashSet<>())
                .setTransactions(new ArrayList<>())
                .setRecurringTransactions(new ArrayList<>())
                .setBudgets(new ArrayList<>())
                .setGoals(new ArrayList<>())
                .build();
        User created = userService.create(user);
        assertNotNull(created);
        createdUserIds.add(created.getUserID());
        // Duplicate username
        User dupUser = new User.UserBuilder().copy(user).setEmail("other@email.com").build();
        Exception ex = assertThrows(IllegalArgumentException.class, () -> userService.create(dupUser));
        assertTrue(ex.getMessage().contains("Username is already taken"));
        // Duplicate email
        User dupEmail = new User.UserBuilder().copy(user).setUserName("otherUser").build();
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> userService.create(dupEmail));
        assertTrue(ex2.getMessage().contains("Email is already in use"));
    }

    @Test
    void read() {
        setAdminAuthentication();
        User user = createTestUser("readUser", "read@email.com", "Password123!", new Role("REGULAR_USER"));
        User found = userService.read(user.getUserID());
        assertNotNull(found);
        assertEquals(user.getUserID(), found.getUserID());
        // Not found
        Exception ex = assertThrows(Exception.class, () -> userService.read(999999L));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void update() {
        setAdminAuthentication();
        User user = createTestUser("updateUser", "update@email.com", "Password123!", new Role("REGULAR_USER"));
        User updated = new User.UserBuilder().copy(user).setUserName("updatedName").setEmail("updated@email.com").build();
        updated = userService.update(updated);
        assertNotNull(updated);
        assertEquals("updatedName", updated.getUserName());
        assertEquals("updated@email.com", updated.getEmail());
        // Not found
        User fake = new User.UserBuilder().copy(user).setUserID(999999L).build();
        Exception ex = assertThrows(Exception.class, () -> userService.update(fake));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void delete() {
        setAdminAuthentication();
        User user = createTestUser("deleteUser", "delete@email.com", "Password123!", new Role("REGULAR_USER"));
        userService.delete(user.getUserID());
        Exception ex = assertThrows(Exception.class, () -> userService.read(user.getUserID()));
        assertTrue(ex.getMessage().contains("not found"));
        // Not found
        Exception ex2 = assertThrows(Exception.class, () -> userService.delete(999999L));
        assertTrue(ex2.getMessage().contains("not found"));
    }

    @Test
    void findAll() {
        setAdminAuthentication();
        int before = userService.findAll().size();
        User user = createTestUser("allUser", "all@email.com", "Password123!", new Role("REGULAR_USER"));
        List<User> all = userService.findAll();
        assertTrue(all.stream().anyMatch(u -> u.getUserID().equals(user.getUserID())));
        assertEquals(before + 1, all.size());
    }

    @Test
    void viewAllRegularUsers() {
        setAdminAuthentication();
        User reg = createTestUser("regUser", "reg@email.com", "Password123!", new Role("REGULAR_USER"));
        User admin = createTestUser("adminUser", "admin@email.com", "Password123!", new Role("ADMIN"));
        List<User> regs = userService.viewAllRegularUsers();
        assertTrue(regs.stream().anyMatch(u -> u.getUserID().equals(reg.getUserID())));
        assertFalse(regs.stream().anyMatch(u -> u.getUserID().equals(admin.getUserID())));
    }

    @Test
    void deleteRegularUserById() {
        setAdminAuthentication();
        User reg = createTestUser("delRegUser", "delreg@email.com", "Password123!", new Role("REGULAR_USER"));
        userService.deleteRegularUserById(reg.getUserID());
        Exception ex = assertThrows(Exception.class, () -> userService.read(reg.getUserID()));
        assertTrue(ex.getMessage().contains("not found"));
        // Try to delete admin
        User admin = createTestUser("delAdminUser", "deladmin@email.com", "Password123!", new Role("ADMIN"));
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> userService.deleteRegularUserById(admin.getUserID()));
        assertTrue(ex2.getMessage().contains("Cannot delete an admin"));
    }

    @Test
    void findByUserName() {
        User user = createTestUser("findUser", "find@email.com", "Password123!", new Role("REGULAR_USER"));
        User found = userService.findByUserName("findUser");
        assertNotNull(found);
        assertEquals(user.getUserID(), found.getUserID());
        Exception ex = assertThrows(Exception.class, () -> userService.findByUserName("noSuchUser"));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void findByEmail() {
        User user = createTestUser("findEmailUser", "findemail@email.com", "Password123!", new Role("REGULAR_USER"));
        User found = userService.findByEmail("findemail@email.com");
        assertNotNull(found);
        assertEquals(user.getUserID(), found.getUserID());
        Exception ex = assertThrows(Exception.class, () -> userService.findByEmail("no@email.com"));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void createCategory() {
        setAdminAuthentication();
        String name = "TestCategory";
        String type = "TestType";
        Category created = userService.createCategory(name, type);
        assertNotNull(created);
        assertNotNull(created.getCategoryId());
        assertEquals(name, created.getName());
        assertEquals(type, created.getType());
        // Clean up
        userService.deleteCategory(created.getCategoryId());
    }

    @Test
    void updateCategory() {
        setAdminAuthentication();
        Category created = userService.createCategory("ToUpdate", "Type1");
        Long id = created.getCategoryId();
        Category updated = userService.updateCategory(id, "UpdatedName", "UpdatedType");
        assertNotNull(updated);
        assertEquals(id, updated.getCategoryId());
        assertEquals("UpdatedName", updated.getName());
        assertEquals("UpdatedType", updated.getType());
        // Clean up
        userService.deleteCategory(id);
    }

    @Test
    void deleteCategory() {
        setAdminAuthentication();
        Category created = userService.createCategory("ToDelete", "Type2");
        Long id = created.getCategoryId();
        Category deleted = userService.deleteCategory(id);
        assertNotNull(deleted);
        assertEquals(id, deleted.getCategoryId());
        // Should not be found after delete
        Exception ex = assertThrows(Exception.class, () -> userService.deleteCategory(id));
        assertTrue(ex.getMessage().contains("not found"));
    }

    @Test
    void viewAllCategories() {
        setAdminAuthentication();
        int before = userService.viewAllCategories().size();
        Category created = userService.createCategory("AllCat", "Type3");
        List<Category> all = userService.viewAllCategories();
        assertTrue(all.stream().anyMatch(c -> c.getCategoryId().equals(created.getCategoryId())));
        assertEquals(before + 1, all.size());
        // Clean up
        userService.deleteCategory(created.getCategoryId());
    }

    @Test
    void viewAnonymizedAnalytics() {
        setAdminAuthentication();
        Map<String, Object> analytics = userService.viewAnonymizedAnalytics();
        assertNotNull(analytics);
        assertTrue(analytics.containsKey("transactionsByCategory"));
        assertTrue(analytics.containsKey("transactionsByType"));
    }

    @Test
    void viewAnonymizedAnalyticsByCategory() {
        setAdminAuthentication();
        String catName = "AnalyticsCat";
        Category cat = userService.createCategory(catName, "Type4");
        Map<String, Object> analytics = userService.viewAnonymizedAnalyticsByCategory(catName);
        assertNotNull(analytics);
        assertEquals(catName, analytics.get("category"));
        assertTrue(analytics.containsKey("totalTransactions"));
        assertTrue(analytics.containsKey("totalAmount"));
        assertTrue(analytics.containsKey("averageAmount"));
        // Clean up
        userService.deleteCategory(cat.getCategoryId());
    }

    @Test
    void viewAnonymizedAnalyticsByDateRange() {
        setAdminAuthentication();
        String start = "2025-01-01";
        String end = "2025-12-31";
        Map<String, Object> analytics = userService.viewAnonymizedAnalyticsByDateRange(start, end);
        assertNotNull(analytics);
        assertTrue(analytics.containsKey("dateRange"));
        assertTrue(analytics.containsKey("chartData"));
    }
}
