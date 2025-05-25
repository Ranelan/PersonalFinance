package za.ac.cput.repository;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.factory.RecurringTransactionFactory;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.class)
class RecurringTransactionRepositoryTest {
    @Autowired
    private RecurringTransactionRepository transactionRepository;

    private RecurringTransaction trans = new RecurringTransactionFactory().createRecurringTransaction(
            "Monthly Subscription", LocalDate.now().plusDays(15));

    @Test

    void a_save(){
        trans = transactionRepository.save(trans);
        assertNotNull(trans);
        System.out.println("Saved: " + trans);
    }
//
//    @Test
//    void b_read() {
//        RecurringTransaction transaction = transactionRepository.save(buildTransaction());
//        Optional<RecurringTransaction> found = transactionRepository.findById(transaction.getRecurringTransactionId());
//        assertTrue(found.isPresent());
//        System.out.println("Found: " + found.get());
//    }
//
//    @Test
//    void c_update() {
//        RecurringTransaction transaction = transactionRepository.save(buildTransaction());
//        RecurringTransaction updated = new RecurringTransaction.RecurringTransactionBuilder()
//                .copy(transaction)
//                .setNextExecution(LocalDate.now().plusDays(10))
//                .build();
//
//        transactionRepository.save(updated);
//        Optional<RecurringTransaction> found = transactionRepository.findById(transaction.getRecurringTransactionId());
//        assertTrue(found.isPresent());
//        assertEquals(LocalDate.now().plusDays(10), found.get().getNextExecution());
//    }
//
//    @Test
//    void d_findAll() {
//        transactionRepository.save(buildTransaction());
//        assertFalse(transactionRepository.findAll().isEmpty());
//        System.out.println("All Transactions: " + transactionRepository.findAll());
//    }
//
}