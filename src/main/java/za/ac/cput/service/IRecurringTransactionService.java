package za.ac.cput.service;

import za.ac.cput.domain.RecurringTransaction;

import java.time.LocalDate;
import java.util.List;

public interface IRecurringTransactionService extends IService<RecurringTransaction, Long> {
    RecurringTransaction read(Long id);
    List<RecurringTransaction> findByRecurrenceType(String recurrenceType);
    List<RecurringTransaction> findByNextExecution(LocalDate nextExecution);
    List<RecurringTransaction> findAll();
}
