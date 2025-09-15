/* GoalRepository.java
   Budget Service Test class
   Author: Ranelani Engel(221813853)
   Date: 25 May 2025 */

package za.ac.cput.service;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Budget;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.RegularUserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BudgetServiceTest {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private RegularUserRepository regularUserRepository;

    private Budget budget;
    private RegularUser regularUser;

    @BeforeEach
    void setUpUser() {
        regularUser = new RegularUser.RegularUserBuilder()
                .setUserName("Ranelani Engel")
                .setEmail("ranelani@example.com")
                .setPassword("securePassword123")
                .build();

        regularUser = regularUserRepository.save(regularUser);
        assertNotNull(regularUser.getUserID(), "User ID should be generated after save");
    }

    @Test
    @Order(1)
    void create() {
        budget = new Budget.BudgetBuilder()
                .setMonth("January")
                .setYear("2025")
                .setLimitAmount(5000.00)
                .setRegularUser(regularUser)
                .build();

        budget = budgetService.create(budget);
        assertNotNull(budget, "Budget should not be null after creation");
        assertNotNull(budget.getBudgetId(), "Budget ID should be generated");
    }

    @Test
    @Order(2)
    void read() {
        Budget found = budgetService.read(budget.getBudgetId());
        assertNotNull(found, "Found budget should not be null");
        assertEquals(budget.getBudgetId(), found.getBudgetId());
    }

    @Test
    @Order(3)
    void update() {
        Budget updatedBudget = new Budget.BudgetBuilder()
                .copy(budget)
                .setMonth("February")
                .setLimitAmount(6000.00)
                .build();

        Budget updated = budgetService.update(updatedBudget);
        assertNotNull(updated, "Updated budget should not be null");
        assertEquals("February", updated.getMonth(), "Month should be updated");

        budget = updated;
    }

//    @Test
//    @Order(4)
//    void findByMonth() {
//        List<Budget> results = budgetService.findByMonth("February");
//        assertFalse(results.isEmpty(), "Should find at least one budget for February");
//        assertEquals("February", results.get(0).getMonth());
//    }
//
//    @Test
//    @Order(5)
//    void findByLimitAmountGreaterThan() {
//        List<Budget> results = budgetService.findByLimitAmountGreaterThan(3000.00);
//        assertFalse(results.isEmpty(), "Should find budgets with limit greater than 3000");
//        assertTrue(results.stream().allMatch(b -> b.getLimitAmount() > 3000.00));
//    }
//
//    @Test
//    @Order(6)
//    void findByYear() {
//        List<Budget> results = budgetService.findByYear("2025");
//        assertFalse(results.isEmpty(), "Should find budgets from the year 2025");
//        assertTrue(results.stream().allMatch(b -> b.getYear().equals("2025")));
//    }

    @Test
    @Order(7)
    void delete() {
        budgetService.delete(budget.getBudgetId());
        Budget deleted = budgetService.read(budget.getBudgetId());
        assertNull(deleted, "Budget should be null after deletion");
    }
}
