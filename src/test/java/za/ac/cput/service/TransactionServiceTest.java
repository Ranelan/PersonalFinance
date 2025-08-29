package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Transaction;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Permission;
import za.ac.cput.factory.CategoryFactory;
import za.ac.cput.factory.UserFactory;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.UserRepository;
import za.ac.cput.repository.RoleRepository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransactionServiceTest {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Transaction transaction;
    private User user;
    private Category category;


    @BeforeEach
    void setUp() {
        // Find or create Role first
        Role role = roleRepository.findByName("REGULAR_USER").orElseGet(() -> roleRepository.save(new Role("REGULAR_USER")));
        // First, create and save the User
        user = UserFactory.createUser(
                "TestUser123",
                "testuser@example.com",
                "Password123!",
                role,
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>(),
                new java.util.ArrayList<>()
        );
        assertNotNull(user, "User creation failed. Check UserFactory and password requirements.");
        user = userRepository.save(user);
        assertNotNull(user.getUserID(), "User ID should be generated");

        // Create Category directly since the factory is not working properly
        category = new Category.CategoryBuilder()
                .setName("Groceries")
                .setType("Expense")
                .build();

        assertNotNull(category, "Category should not be null after creation");
        category = categoryRepository.save(category);
        assertNotNull(category.getCategoryId(), "Category ID should be generated");

        // Create Transaction with the saved category and user
        transaction = new Transaction.TransactionBuilder()
                .setAmount(250.00)
                .setDate(LocalDate.of(2025, 8, 1))
                .setDescription("Pick n Pay groceries")
                .setType("Expense")
                .setUser(user)
                .setCategory(category)
                .build();

        assertNotNull(transaction, "Transaction should not be null after building");
        transaction = transactionService.create(transaction);
        assertNotNull(transaction.getTransactionId(), "Transaction ID should be generated");

        // Update category with transaction reference
        category = new Category.CategoryBuilder()
                .copy(category)
                .setTransaction(transaction)
                .build();
        category = categoryRepository.save(category);
    }

    @Test
    @Order(1)
    void create() {
        // The transaction is already created in setUp(), just verify it here
        assertNotNull(transaction, "Transaction should not be null");
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

        // Update reference for further tests
        transaction = updated;
    }

    @Test
    @Order(4)
    void findAll() {
        List<Transaction> transactions = transactionService.findAll();
        assertFalse(transactions.isEmpty(), "Should find at least one transaction");
        assertTrue(transactions.stream().anyMatch(t -> t.getTransactionId().equals(transaction.getTransactionId())));
    }

//    @Test
//    @Order(5)
//    void delete() {
//        transactionService.delete(transaction.getTransactionId());
//        Transaction deleted = transactionService.read(transaction.getTransactionId());
//        assertNull(deleted, "Transaction should be null after deletion");
//    }
}
