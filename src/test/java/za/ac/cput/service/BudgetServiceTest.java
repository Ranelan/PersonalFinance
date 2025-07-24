/* GoalRepository.java
     Budget Service Test class
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Budget;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    private Budget budget;

//    @BeforeEach
//    void setUp() {
//
//    }

    @Test
    @Order(1)
    void create() {
        budget = new Budget.BudgetBuilder()
                .setBudgetId(1L)
                .setMonth("January")
                .setYear("2025")
                .setLimitAmount(5000.00)
                .build();

        Budget createdBudget = budgetService.create(budget);
        assertNotNull(createdBudget);
        assertEquals(budget.getBudgetId(), createdBudget.getBudgetId());
        assertEquals(budget.getMonth(), createdBudget.getMonth());
        assertEquals(budget.getYear(), createdBudget.getYear());
        assertEquals(budget.getLimitAmount(), createdBudget.getLimitAmount());
    }

    @Test
    @Order(2)
    void read() {
    }

    @Test
    @Order(3)
    void update() {
    }

    @Test
    @Order(7)
    void delete() {
    }

    @Test
    @Order(4)
    void findByMonth() {
    }

    @Test
    @Order(5)
    void findByLimitAmountGreaterThan() {
    }

    @Test
    @Order(6)
    void findByYear() {
    }
}