/* Category.java
   Category Factory class
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 18 May 2025 */

package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Category;

import static org.junit.jupiter.api.Assertions.*;

class CategoryFactoryTest {

    private Long categoryId;
    private String name;
    private String type;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryId = 100L;
        name = "Food";
        type = "Expense";
    }

    @Test
    void createCategory() {
        category = CategoryFactory.createCategory(categoryId, name, type);
        assertNotNull(category);
        assertEquals(categoryId, category.getCategoryId());
        assertEquals(name, category.getName());
        assertEquals(type, category.getType());
    }

    @Test
    void createCategoryWithInvalidName() {
        category = CategoryFactory.createCategory(categoryId, "", type);
        assertNull(category);
    }

    @Test
    void createCategoryWithInvalidType() {
        category = CategoryFactory.createCategory(categoryId, name, "");
        assertNull(category);
    }
}
