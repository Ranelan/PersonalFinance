/* Category.java
   Category Factory class
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 18 May 2025 */

package za.ac.cput.factory;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.Transaction;
import za.ac.cput.util.Helper;

public class CategoryFactory {
    public static Category createCategory( String name, String type, Transaction transaction) {
        if (Helper.isNullOrEmpty(name) || Helper.isNullOrEmpty(type) || transaction == null) {
            return null;
        }

        return new Category.CategoryBuilder()
                .setName(name)
                .setType(type)
                .setTransaction(transaction)
                .build();
    }
}
