package za.ac.cput.factory;

/* BudgetFactoryTest.java
     BudgetFactoryTest class
     Author: Ranelani Engel(221813853)
     Date: 17 May 2025 */

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Budget;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.User;

import static org.junit.jupiter.api.Assertions.*;

class BudgetFactoryTest {

    private String month;
    private String year;
    private double limitAmount;
    private BudgetFactory budgetFactory;
    private Budget budget;
    private User user;

    @BeforeEach
    void setUp() {
        month = "May";
        year = "2025";
        limitAmount = 1000.00;

        user = UserFactory.createUser(
                "Ranelani", "ranelani@example.com", "securePassword123!", new Role("REGULAR_USER"), new java.util.ArrayList<>(), new java.util.ArrayList<>(), new java.util.ArrayList<>(), new java.util.ArrayList<>());
    }

    @Test
    void createBudget() {
        budget = BudgetFactory.createBudget(month, year, limitAmount, user);
        assertNotNull(budget);
        assertEquals(month, budget.getMonth());
        assertEquals(year, budget.getYear());
        assertEquals(limitAmount, budget.getLimitAmount());
        assertEquals(user, budget.getUser());
    }

    @Test
    void createBudgetWithInvalidMonth() {
        budget = BudgetFactory.createBudget("NonExisting", year, limitAmount, user);
        assertNull(budget);
    }

    @Test
    void createBudgetWithInvalidYear() {
        budget = BudgetFactory.createBudget(month, "20T3", limitAmount, user);
        assertNull(budget);
    }

    @Test
    void createBudgetWithInvalidLimitAmount() {
        budget = BudgetFactory.createBudget(month, year, -1000.00, user);
        assertNull(budget);
    }

    @Test
    void createBudgetWithNullUser() {
        budget = BudgetFactory.createBudget(month, year, limitAmount, null);
        assertNull(budget);
    }
}