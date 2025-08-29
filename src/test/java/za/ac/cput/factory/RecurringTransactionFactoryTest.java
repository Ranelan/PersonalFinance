package za.ac.cput.factory;

import za.ac.cput.domain.RecurringTransaction;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Permission;
import za.ac.cput.domain.Transaction;
import za.ac.cput.domain.Budget;
import za.ac.cput.domain.Goal;
import java.util.Collections;
import java.util.HashSet;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RecurringTransactionFactoryTest {
    private String recurrenceType;
    private LocalDate nextExecution;
    private RecurringTransaction transaction;
    private User user;

    @BeforeEach
    void setUp(){
        recurrenceType = "Monthly";
        nextExecution = LocalDate.of(2025, 9, 28);
        // Create a dummy regular user with no permissions and empty lists
        user = UserFactory.createUser(
            "testuser",
            "testuser@email.com",
            "Password123!",
            new Role("REGULAR_USER"),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>(),
            new java.util.ArrayList<>()
        );
    }

    @Test
    void a_createRecurringTransaction(){
        transaction = RecurringTransactionFactory.createRecurringTransaction(recurrenceType, nextExecution, user);

        assertNotNull(transaction);
        assertEquals(recurrenceType, transaction.getRecurrenceType());
        assertEquals(nextExecution, transaction.getNextExecution());
        assertEquals(user, transaction.getUser());
    }


    @Test
    void b_testRecurringTransaction(){
        transaction = RecurringTransactionFactory.createRecurringTransaction(null, null, null);

        assertNull(transaction);
    }

    @Test
    void c_testRecurringTransaction(){
        transaction = RecurringTransactionFactory.createRecurringTransaction("", LocalDate.of(2025, 5, 28), user);

        assertNull(transaction);
    }

}