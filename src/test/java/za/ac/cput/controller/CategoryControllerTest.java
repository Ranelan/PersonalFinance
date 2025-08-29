package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Transaction;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Permission;
import java.util.ArrayList;
import java.util.HashSet;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.TransactionFactory;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.TransactionRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.RoleRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    private Category category;
    private Transaction transaction;
    private User user;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/category";
    }

    @BeforeEach
    void setUp() {
        Role savedRole = roleRepository.findByName("REGULAR_USER").orElseGet(() -> roleRepository.save(new Role("REGULAR_USER")));
        user = za.ac.cput.factory.UserFactory.createUser(
            "testuser",
            "testuser@email.com",
            "Password123!",
            savedRole,
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>()
        );
        user = userRepository.save(user);
        transaction = TransactionFactory.createTransaction(
                250.00,
                LocalDate.of(2025, 8, 10),
                "Grocery purchase",
                "Expense",
                user
        );
        transaction = transactionRepository.save(transaction);
        category = CategoryFactory.createCategory("Food", "Grocery", transaction);
        category = categoryRepository.save(category);
        // Set both sides of the one-to-one relationship
        transaction.setCategory(category);
        transaction = transactionRepository.save(transaction);
        assertNotNull(category);
    }

    @Test
    @Order(1)
    void create() {
        String url = getBaseUrl() + "/create";
        ResponseEntity<Category> response = restTemplate.postForEntity(url, category, Category.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        category = response.getBody();
        assertNotNull(category);
        assertNotNull(category.getCategoryId());
    }

    @Test
    @Order(2)
    void read() {
        assertNotNull(category, "Category should not be null before read test");
        assertNotNull(category.getCategoryId(), "Category ID should not be null before read test");
        System.out.println("Testing read with category ID: " + category.getCategoryId());
        String url = getBaseUrl() + "/read/" + category.getCategoryId();
        ResponseEntity<Category> response = restTemplate.getForEntity(url, Category.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(category.getCategoryId(), response.getBody().getCategoryId());
    }

    @Test
    @Order(3)
    void update() {
        Category updatedCategory = new Category.CategoryBuilder()
                .copy(category)
                .setName("Updated Food")
                .build();

        String url = getBaseUrl() + "/update";
        HttpEntity<Category> request = new HttpEntity<>(updatedCategory);
        ResponseEntity<Category> response = restTemplate.exchange(url, HttpMethod.PUT, request, Category.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Food", response.getBody().getName());

        category = response.getBody();
    }

    @Test
    @Order(4)
    void findByName() {
        String url = getBaseUrl() + "/findByName/" + category.getName();
        ResponseEntity<Category[]> response = restTemplate.getForEntity(url, Category[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(5)
    void findByType() {
        String url = getBaseUrl() + "/findByType/" + category.getType();
        ResponseEntity<Category[]> response = restTemplate.getForEntity(url, Category[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(6)
    void findAll() {
        String url = getBaseUrl() + "/findAll";
        ResponseEntity<Category[]> response = restTemplate.getForEntity(url, Category[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }

    @Test
    @Order(7)
    void delete() {
        String url = getBaseUrl() + "/delete/" + category.getCategoryId();
        restTemplate.delete(url);

        String readUrl = getBaseUrl() + "/read/" + category.getCategoryId();
        ResponseEntity<Category> response = restTemplate.getForEntity(readUrl, Category.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}