package za.ac.cput.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.service.RecurringTransactionService;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.RegularUserRepository;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/recurringTransactions")
public class RecurringTransactionController {

    private final RecurringTransactionService recurringTransactionService;
    private final CategoryRepository categoryRepository;
    private final RegularUserRepository regularUserRepository;

    public RecurringTransactionController(RecurringTransactionService recurringTransactionService, CategoryRepository categoryRepository, RegularUserRepository regularUserRepository) {
        this.recurringTransactionService = recurringTransactionService;
        this.categoryRepository = categoryRepository;
        this.regularUserRepository = regularUserRepository;
    }

    @PostMapping("/create")
    public ResponseEntity<RecurringTransaction> create(@RequestBody RecurringTransaction recurringTransaction) {
        Long userId = recurringTransaction.getRegularUser() != null ? recurringTransaction.getRegularUser().getUserID() : null;
        Long categoryId = recurringTransaction.getCategory() != null ? recurringTransaction.getCategory().getCategoryId() : null;
        if (userId == null || categoryId == null) {
            return ResponseEntity.badRequest().build();
        }
        RegularUser user = regularUserRepository.findById(userId).orElse(null);
        Category category = categoryRepository.findById(categoryId).orElse(null);
        if (user == null || category == null) {
            return ResponseEntity.badRequest().build();
        }
        recurringTransaction = new RecurringTransaction.RecurringTransactionBuilder()
                .setRecurringTransactionId(recurringTransaction.getRecurringTransactionId())
                .setRecurrenceType(recurringTransaction.getRecurrenceType())
                .setNextExecution(recurringTransaction.getNextExecution())
                .setAmount(recurringTransaction.getAmount())
                .setDescription(recurringTransaction.getDescription())
                .setCategory(category)
                .setRegularUser(user)
                .build();
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

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<List<RecurringTransaction>> getByCategory(@PathVariable Long categoryId) {
        Category category = new Category.CategoryBuilder().setId(categoryId).build();
        List<RecurringTransaction> transactions = recurringTransactionService.findByCategory(category);
        return transactions != null && !transactions.isEmpty() ? ResponseEntity.ok(transactions) : ResponseEntity.notFound().build();
    }

    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<RecurringTransaction>> getByUser(@PathVariable Long userId) {
        List<RecurringTransaction> recTransactions = recurringTransactionService.findByUserId(userId);
        if (recTransactions != null && !recTransactions.isEmpty()) {
            return ResponseEntity.ok(recTransactions);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
