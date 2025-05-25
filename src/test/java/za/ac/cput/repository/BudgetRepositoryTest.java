package za.ac.cput.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Budget;
import za.ac.cput.factory.BudgetFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class BudgetRepositoryTest {

    @Autowired
    private BudgetRepository budgetRepository;
    private static Budget budget = BudgetFactory.createBudget(
            "May", "2025", 1000.00);


    @Test
    void a_Create(){
        budget = budgetRepository.save(budget);
        assertNotNull(budget);
        System.out.println("Created: " + budget);
    }

    @Test
    void b_read(){
        Budget saved = budgetRepository.save(budget);
        Optional<Budget> found = budgetRepository.findById(saved.getBudgetId());
        assertTrue(found.isPresent());
        System.out.println("Read: " + found.get());
    }

    @Test
    void c_update(){
        Budget saved = budgetRepository.save(budget);
        Budget updated = new Budget.BudgetBuilder()
                .copy(saved)
                .setLimitAmount(1200.00)
                .build();
        budgetRepository.save(updated);
        Optional<Budget> found = budgetRepository.findById(saved.getBudgetId());
        assertTrue(found.isPresent());
        assertEquals(1200.00, found.get().getLimitAmount());
        System.out.println("Updated: " + found.get());
    }

//    @Test
//    void e_delete(){
//        Budget saved = budgetRepository.save(budget);
//        budgetRepository.deleteById(saved.getBudgetId());
//        Optional<Budget> found = budgetRepository.findById(saved.getBudgetId());
//        assertFalse(found.isPresent());
//        System.out.println("Deleted: " + saved.getBudgetId());
//    }

    @Test
    void d_findAll(){
        Budget saved = budgetRepository.save(budget);
        List<Budget> budgets = budgetRepository.findAll();
        assertFalse(budgets.isEmpty());
        assertFalse(budgets.contains(saved));
        System.out.println("Find All: " + budgets);
    }

    @Test
    void f_findByMonth(){
        Budget saved = budgetRepository.save(budget);
        List<Budget> found = budgetRepository.findByMonth(saved.getMonth());
        assertFalse(found.isEmpty());
        assertFalse(found.contains(saved));
        System.out.println("Find By Month: " + found);
    }

    @Test
    void g_findByYear(){
        Budget saved = budgetRepository.save(budget);
        List<Budget> found = budgetRepository.findByYear(saved.getYear());
        assertFalse(found.isEmpty());
        assertFalse(found.contains(saved));
        System.out.println("Find By Year: " + found);
    }

    @Test
    void h_findByLimitAmountGreaterThan(){
        Budget saved = budgetRepository.save(budget);
        List<Budget> found = budgetRepository.findByLimitAmountGreaterThan(500.00);
        assertFalse(found.isEmpty());
        assertFalse(found.contains(saved));
        System.out.println("Find By Limit Amount Greater Than: " + found);
    }



}
