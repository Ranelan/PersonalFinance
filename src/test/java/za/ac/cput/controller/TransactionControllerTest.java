/*   Transaction Controller Class
     Author: Lebuhang Nyanyantsi (22184353)
     Date: 07 August 2025 */

package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Transaction;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Permission;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.Budget;
import za.ac.cput.domain.Goal;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TransactionControllerTest {

    private Transaction transaction;
    private User user;
    private Category category;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "/api/transactions";
    }

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser(
                "TestUser123",
                "test@example.com",
                "securePassword123",
                new Role("REGULAR_USER"),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>()
        );
        user = userRepository.save(user);


        category = CategoryFactory.createCategory("Groceries", "Expense", transaction);
        category = categoryRepository.save(category);


        transaction = new Transaction.TransactionBuilder()
                .setAmount(250.00)
                .setDate(LocalDate.of(2025, 8, 1))
                .setDescription("Pick n Pay groceries")
                .setType("Expense")
                .setUser(user)
                .setCategory(category)
                .build();


        String url = getBaseUrl() + "/create";
        ResponseEntity<Transaction> response = restTemplate.postForEntity(
                url,
                transaction,
                Transaction.class
        );

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        transaction = response.getBody();
        assertNotNull(transaction);
        assertNotNull(transaction.getTransactionId());
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(transaction);
        assertNotNull(transaction.getTransactionId());
        assertEquals(250.00, transaction.getAmount(), 0.001);
        assertEquals("Expense", transaction.getType());
        assertEquals("TestUser123", transaction.getUser().getUserName());
    }

    @Test
    @Order(2)
    void read() {
        String url = getBaseUrl() + "/read/" + transaction.getTransactionId();
        ResponseEntity<Transaction> response = restTemplate.getForEntity(url, Transaction.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Transaction found = response.getBody();

        assertNotNull(found);
        assertEquals(transaction.getTransactionId(), found.getTransactionId());
        assertEquals("Pick n Pay groceries", found.getDescription());
        assertNotNull(found.getUser().getUserID());
    }

    @Test
    @Order(3)
    void update() {
        Transaction updated = new Transaction.TransactionBuilder()
                .copy(transaction)
                .setDescription("Woolworths groceries")
                .setAmount(300.00)
                .build();

        String url = getBaseUrl() + "/update";
        HttpEntity<Transaction> entity = new HttpEntity<>(updated);
        ResponseEntity<Transaction> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                entity,
                Transaction.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Transaction updatedTransaction = response.getBody();

        assertNotNull(updatedTransaction);
        assertEquals("Woolworths groceries", updatedTransaction.getDescription());
        assertEquals(300.00, updatedTransaction.getAmount(), 0.001);
        transaction = updatedTransaction;
    }

    @Test
    @Order(4)
    void delete() {
        String url = getBaseUrl() + "/delete/" + transaction.getTransactionId();
        restTemplate.delete(url);

        // Verify deletion
        String readUrl = getBaseUrl() + "/read/" + transaction.getTransactionId();
        ResponseEntity<Transaction> response = restTemplate.getForEntity(readUrl, Transaction.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    void getAll() {
        String url = getBaseUrl() + "/all";
        ResponseEntity<Transaction[]> response = restTemplate.getForEntity(url, Transaction[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Transaction[] transactions = response.getBody();

        assertNotNull(transactions);
        assertTrue(transactions.length > 0);
    }

    @Test
    @Order(6)
    void getTransactionsByUser() {
        String url = getBaseUrl() + "/user/" + user.getUserID();
        ResponseEntity<Transaction[]> response = restTemplate.getForEntity(url, Transaction[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Transaction[] transactions = response.getBody();

        assertNotNull(transactions);
        assertTrue(transactions.length > 0);
        assertEquals(user.getUserName(), transactions[0].getUser().getUserName());
    }
}
