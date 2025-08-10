package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.domain.Transaction;
import za.ac.cput.factory.RegularUserFactory;
import za.ac.cput.repository.RegularUserRepository;
import za.ac.cput.repository.TransactionRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RegularUserRepository regularUserRepository;

    private RegularUser regularUser;

    private Category category;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        regularUser = RegularUserFactory.createRegularUser(
                "NewUser",
                "newuser@gmail.com",
                "password123");
        regularUser = regularUserRepository.save(regularUser);

        transaction = new Transaction.TransactionBuilder()
                .setAmount(100.00)
                .setDate(LocalDate.of(2025, 1, 1))
                .setType("Expense")
                .setDescription("Test Transaction")
                .setRegularUser(regularUser)
                .build();

        transaction = transactionRepository.save(transaction);
        assertNotNull(transaction.getTransactionId(), "Transaction ID should be generated after save");

        category = new Category.CategoryBuilder()
                .setName("Test Category")
                .setType("Expense")
                .setTransaction(transaction)
                .build();

        category = categoryService.create(category);
        assertNotNull(category.getCategoryId(), "Category ID should be generated after save");
    }

    @Test
    @Order(1)
    void create() {
        Category newCategory = new Category.CategoryBuilder()
                .setName("New Category")
                .setType("Income")
                .setTransaction(transaction)
                .build();

        Category createdCategory = categoryService.create(newCategory);
        assertNotNull(createdCategory, "Created category should not be null");
        assertNotNull(createdCategory.getCategoryId(), "Category ID should be generated after creation");
        assertEquals("New Category", createdCategory.getName());
        assertEquals("Income", createdCategory.getType());
    }

    @Test
    @Order(2)
    void read() {
        Category foundCategory = categoryService.read(category.getCategoryId());
        assertNotNull(foundCategory, "Found category should not be null");
        assertEquals(category.getCategoryId(), foundCategory.getCategoryId());
    }

    @Test
    @Order(3)
    void update() {
        Category updatedCategory = new Category.CategoryBuilder()
                .copy(category)
                .setName("Updated Category")
                .setType("Updated Type")
                .build();

        Category updated = categoryService.update(updatedCategory);
        assertNotNull(updated, "Updated category should not be null");
        assertEquals("Updated Category", updated.getName(), "Category name should be updated");
        assertEquals("Updated Type", updated.getType(), "Category type should be updated");

        // Update the original category reference
        category = updated;
    }

    @Test
    @Order(4)
    void findByName() {
        List<Category> foundCategories = categoryService.findByName(category.getName());
        assertNotNull(foundCategories, "Found categories should not be null");
        assertFalse(foundCategories.isEmpty(), "Found categories should not be empty");
        assertEquals(category.getName(), foundCategories.get(0).getName(), "Category name should match");
    }

    @Test
    @Order(5)
    void findByType() {
        List<Category> foundCategories = categoryService.findByType(category.getType());
        assertNotNull(foundCategories, "Found categories should not be null");
        assertFalse(foundCategories.isEmpty(), "Found categories should not be empty");
        assertEquals(category.getType(), foundCategories.get(0).getType(), "Category type should match");
    }

    @Test
    @Order(6)
    void findAll() {
        List<Category> allCategories = categoryService.findAll();
        assertNotNull(allCategories, "All categories should not be null");
        assertFalse(allCategories.isEmpty(), "All categories should not be empty");
        assertTrue(allCategories.stream().anyMatch(c -> c.getCategoryId().equals(category.getCategoryId())),
                "Should find the created category in all categories");
    }
}