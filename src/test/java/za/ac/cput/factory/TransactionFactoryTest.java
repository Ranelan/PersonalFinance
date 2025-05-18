package za.ac.cput.factory;

import za.ac.cput.domain.Transaction;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;

public class TransactionFactoryTest {
    private double amount;
    private LocalDate date;
    private String description;
    private String type;
    private Transaction transaction;

    @BeforeEach
    void setUp(){
        amount = 2678.63;
        date = LocalDate.of(2025, 5, 31);
        description = "Bus ticket money to go back home";
        type = "Savings";
    }

    @Test
    void createTransaction(){
        transaction = TransactionFactory.createTransaction(amount, date, description, type);

        assertNotNull(transaction);
        assertNotNull(amount, transaction.getAmount());
        assertNotNull(date, transaction.getDate());
        assertNotNull(description, transaction.getDescription());
        assertNotNull(type, transaction.getType());
    }

    @Test
    void testTransactionAmount(){
        transaction = TransactionFactory.createTransaction(0, date, description, type);

        assertNull(transaction);
    }

    @Test
    void testTransactionDescription(){
        transaction = TransactionFactory.createTransaction(2003.78, date, "", "Savings");
        assertNull(transaction);
    }

}