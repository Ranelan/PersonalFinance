package za.ac.cput.service;

import za.ac.cput.domain.Category;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.RegularUser;

import java.time.LocalDate;
import java.util.List;

public interface IRecurringTransactionService extends IService<RecurringTransaction, Long> {
    RecurringTransaction read(Long id);
    List<RecurringTransaction> findByRecurrenceType(String recurrenceType);
    List<RecurringTransaction> findByNextExecution(LocalDate nextExecution);
    List<RecurringTransaction> findByCategory(Category category);
    List<RecurringTransaction> findByRegularUser(RegularUser regularUser);
    List<RecurringTransaction> findAll();
    List<RecurringTransaction> findByUserId(Long userId);
}
