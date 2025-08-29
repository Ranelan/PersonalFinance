package za.ac.cput.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.MethodName.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurringTransactionControllerTest {

    private RecurringTransaction recurringTransaction;
    private User user;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port + "api/recurringTransactions";
    }

    @BeforeEach
    void setUp() {
        user = UserFactory.createUser(
            "Spring Boot",
            "springbootv2@gmail.com",
            "securePassword123!",
            new Role("REGULAR_USER"),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>()
        );
        user = userRepository.save(user);
        recurringTransaction = new RecurringTransaction.RecurringTransactionBuilder()
                .setRecurrenceType("Monthly")
                .setNextExecution(LocalDate.of(2025, 8, 1))
                .setUser(user)
                .build();

        String url = getBaseUrl() + "/create";
        ResponseEntity<RecurringTransaction> response = restTemplate.postForEntity(url, recurringTransaction, RecurringTransaction.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Error");

        recurringTransaction = response.getBody();
        assertNotNull(recurringTransaction);
        assertNotNull(recurringTransaction.getRecurringTransactionId());
    }

    @Test
    @Order(1)
    void create() {
        assertNotNull(recurringTransaction);
        assertNotNull(recurringTransaction.getRecurringTransactionId());
    }

    @Test
    @Order(2)
    void read() {
        String url = getBaseUrl() + "/read" + recurringTransaction.getRecurringTransactionId();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        String found = response.getBody();
        assertNotNull(found);
        assertEquals(recurringTransaction.getRecurringTransactionId(), found);
    }

    @Test
    @Order(3)
    void update() {
        RecurringTransaction updatedRecurringTransaction = new RecurringTransaction.RecurringTransactionBuilder()
                .copy(recurringTransaction)
                .setRecurrenceType("Yearly")
                .build();

        String url = getBaseUrl() + "/update";
        HttpEntity<RecurringTransaction> entity = new HttpEntity<>(updatedRecurringTransaction);
        ResponseEntity<RecurringTransaction> response = restTemplate.exchange(url, HttpMethod.PUT, entity, RecurringTransaction.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RecurringTransaction updated = response.getBody();
        assertNotNull(updated);
        assertEquals("Yearly", updated.getRecurrenceType());
        recurringTransaction = updated;
    }

    @Test
    @Order(4)
    void delete() {
        String url = getBaseUrl() + "/delete" + recurringTransaction.getRecurringTransactionId();
        restTemplate.delete(url);

        String readUrl = getBaseUrl() + "/read" + recurringTransaction.getRecurringTransactionId();
        ResponseEntity<RecurringTransaction> response = restTemplate.getForEntity(readUrl, RecurringTransaction.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(5)
    void findByRecurrenceType() {
        String url = getBaseUrl() + "/findByRecurrenceType" + recurringTransaction.getRecurrenceType();
        ResponseEntity<RecurringTransaction[]> response = restTemplate.getForEntity(url, RecurringTransaction[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RecurringTransaction[] found = response.getBody();
        assertNotNull(found);
        assertTrue(found.length > 0);

        for (RecurringTransaction recurringTransaction : found) {
            assertEquals("Monthly", recurringTransaction.getRecurrenceType());
        }
    }

    @Test
    @Order(6)
    void findByNextExecution() {
        String url = getBaseUrl() + "/findByNextExecution" + recurringTransaction.getNextExecution();
        ResponseEntity<RecurringTransaction[]> response = restTemplate.getForEntity(url, RecurringTransaction[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RecurringTransaction[] found = response.getBody();
        assertNotNull(found);
        assertTrue(found.length > 0);

        for (RecurringTransaction rt : found) {
            assertEquals(recurringTransaction.getNextExecution(), rt.getNextExecution());
        }
    }

    @Test
    @Order(7)
    void findAll() {
        String url = getBaseUrl() + "/findAll";
        ResponseEntity<RecurringTransaction[]> response = restTemplate.getForEntity(url, RecurringTransaction[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        RecurringTransaction[] all = response.getBody();
        assertNotNull(all);
        assertTrue(all.length > 0);

        boolean found = Arrays.stream(all)
                .anyMatch(rt -> rt.getRecurringTransactionId().equals(recurringTransaction.getRecurringTransactionId()));
        assertTrue(found);

    }
}