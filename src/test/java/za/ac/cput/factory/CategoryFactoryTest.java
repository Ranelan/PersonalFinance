package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Transaction;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CategoryFactoryTest {

    private String name;
    private String type;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        name = "Food";
        type = "Expense";

        transaction = new Transaction.TransactionBuilder()
                .setAmount(120.00)
                .setDate(LocalDate.of(2025, 8, 10))
                .setDescription("KFC lunch")
                .setType("Expense")
                .build();
    }

    @Test
    void createCategorySuccessfully() {
        Category category = CategoryFactory.createCategory(name, type, transaction);
        assertNotNull(category);
        assertEquals(name, category.getName());
        assertEquals(type, category.getType());
        assertEquals(transaction, category.getTransaction());
    }

    @Test
    void createCategoryWithInvalidName() {
        Category category = CategoryFactory.createCategory("", type, transaction);
        assertNull(category);
    }

    @Test
    void createCategoryWithInvalidType() {
        Category category = CategoryFactory.createCategory(name, "", transaction);
        assertNull(category);
    }

    @Test
    void createCategoryWithNullTransaction() {
        Category category = CategoryFactory.createCategory(name, type, null);
        assertNull(category);
    }
}