package za.ac.cput.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Category;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.RoleRepository;
import za.ac.cput.factory.UserFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/personalFinance/api";
    }

    private User adminUser;
    private User regularUser;
    private Role adminRole;
    private Role regularRole;

    @BeforeEach
    void setUp() {
        restTemplate = restTemplate.withBasicAuth("admin", "adminpass");
        userRepository.deleteAll();
        roleRepository.deleteAll();
        adminRole = roleRepository.save(new Role("ADMIN"));
        regularRole = roleRepository.save(new Role("REGULAR_USER"));
        adminUser = new User.UserBuilder()
                .setUserName("admin")
                .setEmail("admin@example.com")
                .setPassword("adminpass")
                .setRole(adminRole)
                .setPermissions(new HashSet<>())
                .setTransactions(new ArrayList<>())
                .setRecurringTransactions(new ArrayList<>())
                .setBudgets(new ArrayList<>())
                .setGoals(new ArrayList<>())
                .build();
        regularUser = new User.UserBuilder()
                .setUserName("user")
                .setEmail("user@example.com")
                .setPassword("userpass")
                .setRole(regularRole)
                .setPermissions(new HashSet<>())
                .setTransactions(new ArrayList<>())
                .setRecurringTransactions(new ArrayList<>())
                .setBudgets(new ArrayList<>())
                .setGoals(new ArrayList<>())
                .build();
        userRepository.save(adminUser);
        userRepository.save(regularUser);
    }

    @Test
    void create() {
        User newUser = new User.UserBuilder()
                .setUserName("newuser")
                .setEmail("newuser@example.com")
                .setPassword("Newuser123!")
                .setRole(regularRole)
                .setPermissions(new HashSet<>())
                .setTransactions(new ArrayList<>())
                .setRecurringTransactions(new ArrayList<>())
                .setBudgets(new ArrayList<>())
                .setGoals(new ArrayList<>())
                .build();
        // UserID is not set, so it will be null by default
        ResponseEntity<User> response = restTemplate.postForEntity(getBaseUrl() + "/users/create", newUser, User.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("newuser", response.getBody().getUserName());
    }

    @Test
    void logIn() {
        Map<String, String> loginRequest = new HashMap<>();
        loginRequest.put("usernameOrEmail", "admin");
        loginRequest.put("password", "adminpass");
        ResponseEntity<User> response = restTemplate.postForEntity(getBaseUrl() + "/users/login", loginRequest, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("admin", response.getBody().getUserName());
    }

    @Test
    void read() {
        ResponseEntity<User> response = restTemplate.getForEntity(getBaseUrl() + "/admin/users/read/" + adminUser.getUserID(), User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("admin", response.getBody().getUserName());
    }

    @Test
    void update() {
        adminUser = new User.UserBuilder()
                .setUserID(adminUser.getUserID())
                .setUserName("adminUpdated")
                .setEmail(adminUser.getEmail())
                .setPassword(adminUser.getPassword())
                .setRole(adminUser.getRole())
                .setPermissions(adminUser.getPermissions())
                .setTransactions(adminUser.getTransactions())
                .setRecurringTransactions(adminUser.getRecurringTransactions())
                .setBudgets(adminUser.getBudgets())
                .setGoals(adminUser.getGoals())
                .build();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<User> entity = new HttpEntity<>(adminUser, headers);
        ResponseEntity<User> response = restTemplate.exchange(getBaseUrl() + "/admin/users/update", HttpMethod.PUT, entity, User.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("adminUpdated", response.getBody().getUserName());
    }

    @Test
    void delete() {
        ResponseEntity<Void> response = restTemplate.exchange(getBaseUrl() + "/admin/users/delete/" + regularUser.getUserID(), HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void findAll() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(getBaseUrl() + "/admin/users/all", User[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length >= 2);
    }

    @Test
    void viewAllRegularUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity(getBaseUrl() + "/admin/users/regular", User[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(Arrays.stream(response.getBody()).allMatch(u -> u.getRole().getName().equals("REGULAR_USER")));
    }

    @Test
    void createCategory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Category> response = restTemplate.postForEntity(getBaseUrl() + "/admin/categories/create?name=TestCategory&type=EXPENSE", entity, Category.class);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("TestCategory", response.getBody().getName());
    }

    @Test
    void updateCategory() {
        // First create a category
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Category> createResponse = restTemplate.postForEntity(getBaseUrl() + "/admin/categories/create?name=ToUpdate&type=INCOME", entity, Category.class);
        Long catId = createResponse.getBody().getCategoryId();
        // Now update
        ResponseEntity<Category> updateResponse = restTemplate.exchange(getBaseUrl() + "/admin/categories/update/" + catId + "?name=UpdatedCategory&type=EXPENSE", HttpMethod.PUT, entity, Category.class);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
        assertNotNull(updateResponse.getBody());
        assertEquals("UpdatedCategory", updateResponse.getBody().getName());
    }

    @Test
    void deleteCategory() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<?> entity = new HttpEntity<>(null, headers);
        ResponseEntity<Category> createResponse = restTemplate.postForEntity(getBaseUrl() + "/admin/categories/create?name=ToDelete&type=INCOME", entity, Category.class);
        Long catId = createResponse.getBody().getCategoryId();
        ResponseEntity<Category> deleteResponse = restTemplate.exchange(getBaseUrl() + "/admin/categories/delete/" + catId, HttpMethod.DELETE, null, Category.class);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertNotNull(deleteResponse.getBody());
        assertEquals("ToDelete", deleteResponse.getBody().getName());
    }

    @Test
    void viewAllCategories() {
        ResponseEntity<Category[]> response = restTemplate.getForEntity(getBaseUrl() + "/admin/categories/all", Category[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getGeneralAnalytics() {
        ResponseEntity<Map> response = restTemplate.getForEntity(getBaseUrl() + "/admin/analytics/general", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void getAnalyticsByCategory() {
        ResponseEntity<Map> response = restTemplate.getForEntity(getBaseUrl() + "/admin/analytics/by-category?categoryName=TestCategory", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAnalyticsByDateRange() {
        ResponseEntity<Map> response = restTemplate.getForEntity(getBaseUrl() + "/admin/analytics/by-date-range?startDate=2025-01-01&endDate=2025-12-31", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getAnalyticsByType() {
        ResponseEntity<Map> response = restTemplate.getForEntity(getBaseUrl() + "/admin/analytics/by-type?transactionType=EXPENSE", Map.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}