package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.User;
import za.ac.cput.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RecurringTransactionServiceTest {

    @Autowired
    private RecurringTransactionService recurringTransactionService;

    @Autowired
    private UserRepository userRepository;

    private RecurringTransaction recurringTransaction;
    private User user;

    @BeforeAll
    void setUpUser() {
        user = new User.UserBuilder()
                .setUserName("Neo Khumalo")
                .setEmail("neok@example.com")
                .setPassword("securePass456*")
                .build();

        user = userRepository.save(user);
        assertNotNull(user.getUserID(), "User ID should be generated");
    }

    @Test
    @Order(1)
    void create() {
        recurringTransaction = new RecurringTransaction.RecurringTransactionBuilder()
                .setRecurrenceType("Monthly")
                .setNextExecution(LocalDate.of(2025, 9, 1))
                .setUser(user)
                .build();

        recurringTransaction = recurringTransactionService.create(recurringTransaction);
        assertNotNull(recurringTransaction, "RecurringTransaction should be created");
        assertNotNull(recurringTransaction.getRecurringTransactionId(), "ID should be generated");
    }

    @Test
    @Order(2)
    void read() {
        RecurringTransaction found = recurringTransactionService.read(recurringTransaction.getRecurringTransactionId());
        assertNotNull(found, "Should find recurring transaction by ID");
        assertEquals(recurringTransaction.getRecurringTransactionId(), found.getRecurringTransactionId());
    }

    @Test
    @Order(3)
    void update() {
        User managedUser = userRepository.findById(user.getUserID())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        RecurringTransaction updatedTransaction = new RecurringTransaction.RecurringTransactionBuilder()
                .copy(recurringTransaction)
                .setRecurrenceType("Weekly")
                .setUser(managedUser)
                .build();

        RecurringTransaction updated = recurringTransactionService.update(updatedTransaction);
        assertNotNull(updated, "Updated transaction should not be null");
        assertEquals("Weekly", updated.getRecurrenceType(), "Recurrence type should be updated");

        recurringTransaction = updated;
    }

    @Test
    @Order(4)
    void findByRecurrenceType() {
        List<RecurringTransaction> results = recurringTransactionService.findByRecurrenceType("Weekly");
        assertFalse(results.isEmpty(), "Should find transactions by recurrence type");
        assertEquals("Weekly", results.get(0).getRecurrenceType());
    }

    @Test
    @Order(5)
    void findByNextExecution() {
        List<RecurringTransaction> results = recurringTransactionService.findByNextExecution(recurringTransaction.getNextExecution());
        assertFalse(results.isEmpty(), "Should find transactions by next execution date");
        assertEquals(recurringTransaction.getNextExecution(), results.get(0).getNextExecution());
    }

    @Test
    @Order(6)
    void findAll() {
        List<RecurringTransaction> allTransactions = recurringTransactionService.findAll();
        assertNotNull(allTransactions, "List of all transactions should not be null");
        assertTrue(allTransactions.stream().anyMatch(rt -> rt.getRecurringTransactionId().equals(recurringTransaction.getRecurringTransactionId())),
                "Created transaction should be in the list");
    }

//    @Test
//    @Order(7)
//    void delete() {
//        recurringTransactionService.delete(recurringTransaction.getRecurringTransactionId());
//        RecurringTransaction deleted = recurringTransactionService.read(recurringTransaction.getRecurringTransactionId());
//        assertNull(deleted, "Transaction should be null after deletion");
//    }

}