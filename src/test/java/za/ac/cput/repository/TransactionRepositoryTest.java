package za.ac.cput.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.Transaction;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository repository;

    private final Transaction transaction = new Transaction.TransactionBuilder()
            .setAmount(250.00)
            .setDate(LocalDate.now())
            .setDescription("Monthly Subscription")
            .setType("Expense")
            .build();

    private final Transaction anotherTransaction = new Transaction.TransactionBuilder()
            .setAmount(1000.00)
            .setDate(LocalDate.now().minusDays(10))
            .setDescription("Salary Payment")
            .setType("Income")
            .build();

    @Test
    void a_save() {
        Transaction saved = repository.save(transaction);
        assertNotNull(saved);
        System.out.println("Saved Transaction: " + saved);

        Transaction saved2 = repository.save(anotherTransaction);
        assertNotNull(saved2);
        System.out.println("Saved Transaction: " + saved2);
    }

    @Test
    void b_findByType() {
        List<Transaction> expenses = repository.findByType("Expense");
        assertFalse(expenses.isEmpty());
        System.out.println("Found Expenses: " + expenses);
    }

    @Test
    void c_findByAmountGreaterThan() {
        List<Transaction> largeTransactions = repository.findByAmountGreaterThan(200.00);
        assertFalse(largeTransactions.isEmpty());
        System.out.println("Transactions > 200: " + largeTransactions);
    }

    @Test
    void d_findByDateBetween() {
        LocalDate start = LocalDate.now().minusDays(15);
        LocalDate end = LocalDate.now();
        List<Transaction> recentTransactions = repository.findByDateBetween(start, end);
        assertFalse(recentTransactions.isEmpty());
        System.out.println("Transactions in last 15 days: " + recentTransactions);
    }

    @Test
    void e_deleteById() {
        Transaction toDelete = repository.save(new Transaction.TransactionBuilder()
                .setAmount(55.0)
                .setDate(LocalDate.now())
                .setDescription("Temporary Transaction")
                .setType("Expense")
                .build());

        Long id = toDelete.getTransactionId();
        repository.deleteById(id);

        Optional<Transaction> deleted = repository.findById(id);
        assertFalse(deleted.isPresent());
        System.out.println("Deleted Transaction with ID: " + id);
    }

}