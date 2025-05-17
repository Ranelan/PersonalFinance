package za.ac.cput.factory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.Budget;

import static org.junit.jupiter.api.Assertions.*;

class BudgetFactoryTest {

    private String month;
    private String year;
    private double limitAmount;
    private BudgetFactory budgetFactory;
    private Budget budget;

    @BeforeEach
    void setUp() {
        month = "May";
        year = "2025";
        limitAmount = 1000.00;

    }

    @Test
    void createBudget() {
        budget = BudgetFactory.createBudget(month, year, limitAmount);
        assertNotNull(budget);
        assertEquals(month, budget.getMonth());
        assertEquals(year, budget.getYear());
        assertEquals(limitAmount, budget.getLimitAmount());

    }

    @Test
    void createBudgetWithInvalidMonth() {
        budget = BudgetFactory.createBudget("NonExisting", year, limitAmount);
        assertNull(budget);
    }

    @Test
    void createBudgetWithInvalidYear() {
        budget = BudgetFactory.createBudget(month, "20T3", limitAmount);
        assertNull(budget);
    }

    @Test
    void createBudgetWithInvalidLimitAmount() {
        budget = BudgetFactory.createBudget(month, year, -1000.00);
        assertNull(budget);
    }
}