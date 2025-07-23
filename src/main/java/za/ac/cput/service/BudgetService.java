package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Budget;
import za.ac.cput.factory.BudgetFactory;
import za.ac.cput.repository.BudgetRepository;

import java.util.List;

@Service
public class BudgetService implements IBudgetService{


    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget create(Budget budget) {
        return budgetRepository.save(budget);
    }

    @Override
    public Budget read(Long aLong) {
        return budgetRepository.findById(aLong).orElse(null);
    }

    @Override
    public Budget update(Budget budget) {
        if(budget.getBudgetId() != null && budgetRepository.existsById(budget.getBudgetId())) {
            Budget existing = budgetRepository.findById(budget.getBudgetId()).orElse(null);
            if(existing != null) {
                Budget updated = new Budget.BudgetBuilder()
                        .copy(existing)
                        .setMonth(budget.getMonth())
                        .setYear(budget.getYear())
                        .setLimitAmount(budget.getLimitAmount())
                        .build();

                return budgetRepository.save(updated);
            }
        }
        return null;
    }

    @Override
    public void delete(Long aLong) {
        try {
            budgetRepository.deleteById(aLong);
        } catch (Exception e) {
            System.out.println("Error deleting budget: " + e.getMessage());
        }
    }

    @Override
    public List<Budget> findByMonth(String month) {
        return budgetRepository.findByMonth(month);
    }

    @Override
    public List<Budget> findByLimitAmountGreaterThan(double amount) {
        return budgetRepository.findByLimitAmountGreaterThan(amount);
    }

    @Override
    public List<Budget> findByYear(String year) {
        return budgetRepository.findByYear(year);
    }
}
