package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Goal;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.repository.RecurringTransactionRepository;
import za.ac.cput.repository.RegularUserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class RecurringTransactionService implements IRecurringTransactionService{

    private final RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    public RecurringTransactionService(RecurringTransactionRepository recurringTransactionRepository) {
        this.recurringTransactionRepository = recurringTransactionRepository;
    }

    @Override
    public RecurringTransaction create(RecurringTransaction recurringTransaction) {
        return recurringTransactionRepository.save(recurringTransaction);
    }

    @Override
    public RecurringTransaction read(Long aLong) {
        return recurringTransactionRepository.findById(aLong).orElse(null);
    }

    @Override
    public RecurringTransaction update(RecurringTransaction recurringTransaction) {
        if (recurringTransaction.getRecurringTransactionId() != null && recurringTransactionRepository.existsById((recurringTransaction.getRecurringTransactionId()))) {
            return recurringTransactionRepository.save(recurringTransaction);
        }
        return null;
    }

    @Override
    public void delete(Long aLong) {
        recurringTransactionRepository.deleteById(aLong);
    }

    @Override
    public List<RecurringTransaction> findByRecurrenceType(String recurrenceType) {
        return recurringTransactionRepository.findByRecurrenceType(recurrenceType);
    }

    @Override
    public List<RecurringTransaction> findByNextExecution(LocalDate nextExecution) {
        return recurringTransactionRepository.findByNextExecution(nextExecution);
    }

    @Override
    public List<RecurringTransaction> findAll() {
        return  recurringTransactionRepository.findAll();
    }
}
