package za.ac.cput.factory;

import za.ac.cput.domain.Transaction;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class TransactionFactory {
    public static Transaction createTransaction(double amount, LocalDate date, String description, String type) {
        if (Helper.isValidCurrentAmount(amount) || Helper.isNullOrEmpty(String.valueOf(date)) ||
        Helper.isNullOrEmpty(description) || Helper.isNullOrEmpty(type)){
            return null;
        }

        return new Transaction.TransactionBuilder()
                .setAmount(amount)
                .setDate(date)
                .setDescription(description)
                .setType(type)
                .build();
    }
}
