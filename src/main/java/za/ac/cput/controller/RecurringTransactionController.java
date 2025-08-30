package za.ac.cput.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.service.RecurringTransactionService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recurring-transaction")
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;

    public RecurringTransactionController(RecurringTransactionService recurringTransactionService) {
        this.recurringTransactionService = recurringTransactionService;
    }

    @PostMapping("/create")
    public ResponseEntity<RecurringTransaction> create(@RequestBody RecurringTransaction recurringTransaction) {
        RecurringTransaction createdRecurringTransaction = recurringTransactionService.create(recurringTransaction);
        return createdRecurringTransaction != null ? ResponseEntity.ok(createdRecurringTransaction) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/read{id}")
    public ResponseEntity<RecurringTransaction> read(@PathVariable Long id) {
        RecurringTransaction recurringTransaction = recurringTransactionService.read(id);
        return recurringTransaction != null ? ResponseEntity.ok(recurringTransaction) : ResponseEntity.notFound().build();
    }

    @PutMapping("/update")
    public ResponseEntity<RecurringTransaction> update(@RequestBody RecurringTransaction recurringTransaction) {
        RecurringTransaction updatedRecurringTransaction = recurringTransactionService.update(recurringTransaction);
        return updatedRecurringTransaction != null ? ResponseEntity.ok(updatedRecurringTransaction) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            recurringTransactionService.delete(id);
            return ResponseEntity.noContent().build();
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByRecurrenceType/{type}")
    public ResponseEntity<List<RecurringTransaction>> findByRecurrenceType(@PathVariable String type) {
        List<RecurringTransaction> recurringTransactions = recurringTransactionService.findByRecurrenceType(type);
        return recurringTransactions != null && !recurringTransactions.isEmpty() ? ResponseEntity.ok(recurringTransactions) : ResponseEntity.notFound().build();

    }
    @GetMapping("/findByNextExecution/{nextExecution}")
    public  ResponseEntity<List<RecurringTransaction>> findByNextExecution(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate nextExecution) {
        List<RecurringTransaction> recurringTransactions = recurringTransactionService.findByNextExecution(nextExecution);
        return recurringTransactions != null && !recurringTransactions.isEmpty() ? ResponseEntity.ok(recurringTransactions) : ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<RecurringTransaction>> findAll() {
        List<RecurringTransaction> recurringTransactions = recurringTransactionService.findAll();
        return recurringTransactions != null && !recurringTransactions.isEmpty() ? ResponseEntity.ok(recurringTransactions) : ResponseEntity.noContent().build();
    }
}
