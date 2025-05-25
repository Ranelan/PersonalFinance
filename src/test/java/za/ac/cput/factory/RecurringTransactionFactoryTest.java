package za.ac.cput.factory;

import za.ac.cput.domain.RecurringTransaction;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecurringTransactionFactoryTest {
    private String recurrenceType;
    private LocalDate nextExecution;
    private RecurringTransaction transaction;

    @BeforeEach
    void setUp(){
        recurrenceType = "Monthly";
        nextExecution = LocalDate.of(2025, 5, 28);
    }

    @Test
    void createRecurringTransaction(){
        transaction = RecurringTransactionFactory.createRecurringTransaction(recurrenceType, nextExecution);

        assertNotNull(transaction);
        assertNotNull(recurrenceType, transaction.getRecurrenceType());
        assertEquals(nextExecution, transaction.getNextExecution());
    }

    @Test
    void a_testRecurringTransaction(){
        transaction = RecurringTransactionFactory.createRecurringTransaction(null, null);

        assertNull(transaction);
    }

    @Test
    void b_testRecurringTransaction(){
        transaction = RecurringTransactionFactory.createRecurringTransaction("", LocalDate.of(2025, 5, 28));

        assertNull(transaction);
    }

}