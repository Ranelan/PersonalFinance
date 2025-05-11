/* Budget.java
     Budget POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

public class Budget {
    private Long budgetId;
    private String month;
    private int year;
    private double limitAmount;

    public Budget() {

    }

    public Budget(Long budgetId, String month, int year, double limitAmount) {
        this.budgetId = budgetId;
        this.month = month;
        this.year = year;
        this.limitAmount = limitAmount;
    }

    public Budget(BudgetBuilder budgetBuilder) {
        this.budgetId = budgetBuilder.budgetId;
        this.month = budgetBuilder.month;
        this.year = budgetBuilder.year;
        this.limitAmount = budgetBuilder.limitAmount;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public String getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    public static class BudgetBuilder {
        private Long budgetId;
        private String month;
        private int year;
        private double limitAmount;

        public BudgetBuilder setBudgetId(Long budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public BudgetBuilder setMonth(String month) {
            this.month = month;
            return this;
        }

        public BudgetBuilder setYear(int year) {
            this.year = year;
            return this;
        }

        public BudgetBuilder setLimitAmount(double limitAmount) {
            this.limitAmount = limitAmount;
            return this;
        }

        public BudgetBuilder copy(Budget budget) {
            this.budgetId = budget.budgetId;
            this.month = budget.month;
            this.year = budget.year;
            this.limitAmount = budget.limitAmount;
            return this;
        }

        public Budget build() {
            return new Budget(this);
        }
    }
}
