/* Budget.java
     Budget POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long budgetId;
    private String month;
    private String year;
    private double limitAmount;

    public Budget() {

    }

    public Budget(Long budgetId, String month, String year, double limitAmount) {
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

    public String getYear() {
        return year;
    }

    public double getLimitAmount() {
        return limitAmount;
    }

    @Override
    public String toString() {
        return "Budget{" +
                "budgetId=" + budgetId +
                ", month='" + month + '\'' +
                ", year=" + year +
                ", limitAmount=" + limitAmount +
                '}';
    }

    public static class BudgetBuilder {
        private Long budgetId;
        private String month;
        private String year;
        private double limitAmount;

        public BudgetBuilder setBudgetId(Long budgetId) {
            this.budgetId = budgetId;
            return this;
        }

        public BudgetBuilder setMonth(String month) {
            this.month = month;
            return this;
        }

        public BudgetBuilder setYear(String year) {
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
