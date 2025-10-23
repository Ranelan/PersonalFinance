package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.domain.Transaction;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.RegularUserFactory;
import za.ac.cput.factory.TransactionFactory;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.RegularUserRepository;
import za.ac.cput.repository.TransactionRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryControllerTest {

    private Category category;
    private Transaction transaction;
    private RegularUser regularUser;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/category";
    }

    @BeforeEach
    void setUp() {
        regularUser = RegularUserFactory.createRegularUser(
            "testuser",
            "testuser@example.com",
            "password123"
        );
        regularUser = regularUserRepository.save(regularUser);
        transaction = TransactionFactory.createTransaction(
                250.00,
                LocalDate.of(2025, 8, 10),
                "Expense",
                "Groceries"
        );
        transaction = transactionRepository.save(transaction);
        category = CategoryFactory.createCategory("Food", "Grocery purchase", transaction, regularUser);
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
        System.out.println("Category ID: " + category.getCategoryId());

        String url = getBaseUrl() + "/read/" + category.getCategoryId();
        System.out.println("Reading from URL: " + url);

        try {
            ResponseEntity<Category> response = restTemplate.getForEntity(url, Category.class);
            System.out.println("Response Status: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody());

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertNotNull(response.getBody());
            assertEquals(category.getCategoryId(), response.getBody().getCategoryId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
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
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    @Order(8)
    void findByUserId() {
        String url = getBaseUrl() + "/byUser/" + regularUser.getUserID();
        ResponseEntity<Category[]> response = restTemplate.getForEntity(url, Category[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
        for (Category c : response.getBody()) {
            assertEquals(regularUser.getUserID(), c.getRegularUser().getUserID());
        }
    }
}