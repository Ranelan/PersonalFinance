/* CategoryRepositoryTest.java
   Category Repository Test
   Author: Lebuhang Nyanyantsi (222184353)
   Date: 25 May 2025 */

package za.ac.cput.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Category;
import za.ac.cput.factory.CategoryFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;
    private static Category category = new CategoryFactory().createCategory(
            "Groceries", "Expense");

   @Test
    void a_Create() {
        category = categoryRepository.save(category);
        assertNotNull(category);
        System.out.println("Created: " + category);
    }

    @Test
    void b_read() {
        Category saved = categoryRepository.save(category);
        Optional<Category> found = categoryRepository.findById(saved.getCategoryId());
        assertTrue(found.isPresent());
        System.out.println("Read: " + found.get());
    }

    @Test
    void c_update() {
        Category saved = categoryRepository.save(category);
        Category updated = new Category.CategoryBuilder()
                .copy(saved)
                .setName("Utilities")
                .setType("Expense")
                .build();
        categoryRepository.save(updated);
        Optional<Category> found = categoryRepository.findById(saved.getCategoryId());
        assertTrue(found.isPresent());
        assertNotEquals("Utilities", found.get().getName());
        System.out.println("Updated: " + found.get());
    }

    @Test
    void d_delete() {
        Category saved = categoryRepository.save(category);
        categoryRepository.deleteById(saved.getCategoryId());
        Optional<Category> deleted = categoryRepository.findById(saved.getCategoryId());
        assertFalse(deleted.isPresent());
    }
}
