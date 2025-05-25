/* CategoryRepositoryTest.java
   Category Repository Test
   Author: Lebuhang Nyanyantsi (222184353)
   Date: 25 May 2025 */

package za.ac.cput.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.ac.cput.domain.Category;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category1;
    private Category category2;

    @BeforeEach
    void setUp() {
        category1 = new Category.CategoryBuilder()
                .setName("Groceries")
                .setType("Expense")
                .build();

        category2 = new Category.CategoryBuilder()
                .setName("Transport")
                .setType("Expense")
                .build();

        categoryRepository.save(category1);
        categoryRepository.save(category2);
    }

    @Test
    void testFindByName() {
        List<Category> foundList = categoryRepository.findByName("Groceries");
        assertFalse(foundList.isEmpty());
        assertEquals("Groceries", foundList.get(0).getName());
    }


    @Test
    void testFindByNameContainingIgnoreCase() {
        List<Category> result = categoryRepository.findByNameContainingIgnoreCase("trans");
        assertFalse(result.isEmpty());
        assertEquals("Transport", result.get(0).getName());
    }

    @Test
    void testFindByType() {
        List<Category> expenses = categoryRepository.findByType("Expense");
        assertEquals(2, expenses.size());
    }

    @Test
    void testFindById() {
        Optional<Category> found = categoryRepository.findById(category1.getCategoryId());
        assertTrue(found.isPresent());
        assertEquals("Groceries", found.get().getName());
    }

    @Test
    void testDeleteCategory() {
        categoryRepository.deleteById(category2.getCategoryId());
        Optional<Category> deleted = categoryRepository.findById(category2.getCategoryId());
        assertFalse(deleted.isPresent());
    }
}
