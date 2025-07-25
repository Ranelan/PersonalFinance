/* GoalRepository.java
     Budget Controller class
     Author: Ranelani Engel(221813853
     Date: 25 May 2025 */

package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Budget;
import za.ac.cput.service.BudgetService;
import za.ac.cput.service.IBudgetService;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final IBudgetService budgetService;

    @Autowired
    public BudgetController(IBudgetService budgetService) {
        this.budgetService = budgetService;
    }


    @PostMapping("/create")
    public ResponseEntity<Budget> create(@RequestBody Budget budget){
        Budget createdBudget = budgetService.create(budget);
        if (createdBudget != null) {
            return ResponseEntity.ok(createdBudget);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Budget> read(@PathVariable Long id) {
        Budget budget = budgetService.read(id);
        if (budget != null) {
            return ResponseEntity.ok(budget);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Budget> update(@RequestBody Budget budget) {
        Budget updatedBudget = budgetService.update(budget);
        if (updatedBudget != null) {
            return ResponseEntity.ok(updatedBudget);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        budgetService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByMonth/{month}")
    public ResponseEntity<List<Budget>> findByMonth(@PathVariable String month) {
        List<Budget> budgets = budgetService.findByMonth(month);
        if (budgets != null && !budgets.isEmpty()) {
            return ResponseEntity.ok(budgets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByLimitAmountGreaterThan/{amount}")
    public ResponseEntity<List<Budget>> findByLimitAmountGreaterThan(@PathVariable double amount) {
        List<Budget> budgets = budgetService.findByLimitAmountGreaterThan(amount);
        if (budgets != null && !budgets.isEmpty()) {
            return ResponseEntity.ok(budgets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByYear/{year}")
    public ResponseEntity<List<Budget>> findByYear(@PathVariable String year) {
        List<Budget> budgets = budgetService.findByYear(year);
        if (budgets != null && !budgets.isEmpty()) {
            return ResponseEntity.ok(budgets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Budget>> findAll() {
        List<Budget> budgets = budgetService.findAll();
        if (budgets != null && !budgets.isEmpty()) {
            return ResponseEntity.ok(budgets);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
