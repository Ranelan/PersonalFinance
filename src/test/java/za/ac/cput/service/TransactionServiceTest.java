/*
 * TransactionServiceTest.java
 * Transaction Service Test class
 * Author: Lebuhang Nyanyantsi 222184353
 * Date: 7 August 2025
 */

package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.domain.Transaction;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.RegularUserFactory;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.RegularUserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private RegularUserRepository regularUserRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Transaction transaction;
    private RegularUser regularUser;
    private Category category;

    @BeforeEach
    void setUp() {
        regularUser = RegularUserFactory.createRegularUser(
                "TestUser123",
                "testuser@example.com",
                "securePassword123"
        );
        regularUser = regularUserRepository.save(regularUser);
        assertNotNull(regularUser.getUserID(), "RegularUser ID should be generated");

        category = CategoryFactory.createCategory("Groceries", "Expense");
        category = categoryRepository.save(category);
        assertNotNull(category.getCategoryId(), "Category ID should be generated");
    }

    @Test
    @Order(1)
    void create() {
        transaction = new Transaction.TransactionBuilder()
                .setAmount(250.00)
                .setDate(LocalDate.of(2025, 8, 1))
                .setDescription("Pick n Pay groceries")
                .setType("Expense")
                .setRegularUser(regularUser)
                .setCategory(category)
                .build();

        transaction = transactionService.create(transaction);
        assertNotNull(transaction, "Transaction should not be null after creation");
        assertNotNull(transaction.getTransactionId(), "Transaction ID should be generated");
    }

    @Test
    @Order(2)
    void read() {
        Transaction found = transactionService.read(transaction.getTransactionId());
        assertNotNull(found, "Found transaction should not be null");
        assertEquals(transaction.getTransactionId(), found.getTransactionId());
    }

    @Test
    @Order(3)
    void update() {
        Transaction updatedTransaction = new Transaction.TransactionBuilder()
                .copy(transaction)
                .setAmount(300.00)
                .setDescription("Woolworths groceries")
                .build();

        Transaction updated = transactionService.update(updatedTransaction);
        assertNotNull(updated, "Updated transaction should not be null");
        assertEquals(300.00, updated.getAmount(), 0.001);
        assertEquals("Woolworths groceries", updated.getDescription());

        transaction = updated;
    }

    @Test
    @Order(4)
    void findAll() {
        List<Transaction> transactions = transactionService.findAll();
        assertFalse(transactions.isEmpty(), "Should find at least one transaction");
        assertTrue(transactions.stream().anyMatch(t -> t.getTransactionId().equals(transaction.getTransactionId())));
    }

    @Test
    @Order(5)
    void delete() {
        transactionService.delete(transaction.getTransactionId());
        Transaction deleted = transactionService.read(transaction.getTransactionId());
        assertNull(deleted, "Transaction should be null after deletion");
    }
}
