/* BudgetFactory.java
   BudgetFactory class
   Author: Ranelani Engel (221813853)
   Date: 17 May 2025 */

package za.ac.cput.factory;

import za.ac.cput.domain.Budget;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

public class BudgetFactory {

    public static Budget createBudget(String month, String year, double limitAmount, User regularUser) {
        if (!Helper.isValidMonth(month)) {
            System.out.println("Invalid month: '" + month + "'");
            return null;
        }
        if (!Helper.isValidYear(year)) {
            System.out.println("Invalid year: '" + year + "'");
            return null;
        }
        if (!Helper.isValidLimitAmount(limitAmount)) {
            System.out.println("Invalid limitAmount: '" + limitAmount + "'");
            return null;
        }
        if (regularUser == null) {
            System.out.println("User is null");
            return null;
        }
        return new Budget.BudgetBuilder()
                .setMonth(month)
                .setYear(year)
                .setLimitAmount(limitAmount)
                .setUser(regularUser)
                .build();
    }
}
