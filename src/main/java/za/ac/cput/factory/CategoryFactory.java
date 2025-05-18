/* Category.java
   Category Factory class
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 18 May 2025 */

package za.ac.cput.factory;

import za.ac.cput.domain.Category;
import za.ac.cput.util.Helper;

public class CategoryFactory {
    public static Category createCategory(Long categoryId, String name, String type) {
        if (Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(type)) {
            return null;
        }

        return new Category.CategoryBuilder()
                .setCategoryId(categoryId)
                .setName(name)
                .setType(type)
                .build();
    }
}
