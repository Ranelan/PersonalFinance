/* Budget.java
     Budget POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */

package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private User user;

    public Budget() {

    }

    public Budget(Long budgetId, String month, String year, double limitAmount, User user) {
        this.budgetId = budgetId;
        this.month = month;
        this.year = year;
        this.limitAmount = limitAmount;
        this.user = user;
    }

    public Budget(BudgetBuilder budgetBuilder) {
        this.budgetId = budgetBuilder.budgetId;
        this.month = budgetBuilder.month;
        this.year = budgetBuilder.year;
        this.limitAmount = budgetBuilder.limitAmount;
        this.user = budgetBuilder.user;
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

    public User getUser() {
        return user;
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
                ", user=" + user +
                '}';
    }

    public static class BudgetBuilder {
        private Long budgetId;
        private String month;
        private String year;
        private double limitAmount;
        private User user;

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

        public BudgetBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public BudgetBuilder copy(Budget budget) {
            this.budgetId = budget.budgetId;
            this.month = budget.month;
            this.year = budget.year;
            this.limitAmount = budget.limitAmount;
            this.user = budget.user;
            return this;
        }

        public Budget build() {
            return new Budget(this);
        }
    }
}
