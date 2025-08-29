package za.ac.cput.factory;

import za.ac.cput.domain.Transaction;
import za.ac.cput.domain.User;
import za.ac.cput.domain.Role;
import za.ac.cput.domain.Permission;
import java.util.ArrayList;
import java.util.HashSet;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionFactoryTest {
    private double amount;
    private LocalDate date;
    private String description;
    private String type;
    private Transaction transaction;
    private User user;

    @BeforeEach
    void setUp(){
        amount = 2678.63;
        date = LocalDate.of(2025, 5, 31);
        description = "Bus ticket money to go back home";
        type = "Savings";
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
    void createTransaction(){
        transaction = TransactionFactory.createTransaction(amount, date, description, type, user);

        assertNotNull(transaction);
        assertEquals(amount, transaction.getAmount());
        assertEquals(date, transaction.getDate());
        assertEquals(description, transaction.getDescription());
        assertEquals(type, transaction.getType());
        assertEquals(user, transaction.getUser());
    }

    @Test
    void testTransactionAmount(){
        transaction = TransactionFactory.createTransaction(0, date, description, type, user);
        assertNull(transaction);
    }

    @Test
    void testTransactionDescription(){
        transaction = TransactionFactory.createTransaction(2003.78, date, "", "Savings", user);
        assertNull(transaction);
    }

}