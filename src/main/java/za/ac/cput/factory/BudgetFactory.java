/* BudgetFactory.java
     BudgetFactory class
     Author: Ranelani Engel(221813853)
     Date: 17 May 2025 */

package za.ac.cput.factory;

import za.ac.cput.domain.Budget;
import za.ac.cput.util.Helper;

public class BudgetFactory {

    public static Budget createBudget(String month, String year, double limitAmount) {
        if(!Helper.isValidMonth(month)||
           !Helper.isValidYear(year)||
           !Helper.isValidLimitAmount(limitAmount)) {
            return null;
        }
        return new Budget.BudgetBuilder()
                .setMonth(month)
                .setYear(year)
                .setLimitAmount(limitAmount)
                .build();
    }
}
