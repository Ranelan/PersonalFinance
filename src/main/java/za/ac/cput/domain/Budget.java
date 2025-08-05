/* Budget.java
     Budget POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

import jakarta.persistence.*;

@Entity
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long budgetId;
    private String month;
    private String year;
    private double limitAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private RegularUser regularUser;

    public Budget() {

    }

    public Budget(Long budgetId, String month, String year, double limitAmount, RegularUser regularUser) {
        this.budgetId = budgetId;
        this.month = month;
        this.year = year;
        this.limitAmount = limitAmount;
        this.regularUser = regularUser;
    }

    public Budget(BudgetBuilder budgetBuilder) {
        this.budgetId = budgetBuilder.budgetId;
        this.month = budgetBuilder.month;
        this.year = budgetBuilder.year;
        this.limitAmount = budgetBuilder.limitAmount;
        this.regularUser = budgetBuilder.regularUser;
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

    public RegularUser getRegularUser() {
        return regularUser;
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
                ", regularUser=" + regularUser +
                '}';
    }

    public static class BudgetBuilder {
        private Long budgetId;
        private String month;
        private String year;
        private double limitAmount;
        private RegularUser regularUser;

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

        public BudgetBuilder setRegularUser(RegularUser regularUser) {
            this.regularUser = regularUser;
            return this;
        }

        public BudgetBuilder copy(Budget budget) {
            this.budgetId = budget.budgetId;
            this.month = budget.month;
            this.year = budget.year;
            this.limitAmount = budget.limitAmount;
            this.regularUser = budget.regularUser;
            return this;
        }

        public Budget build() {
            return new Budget(this);
        }
    }
}
