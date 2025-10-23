package za.ac.cput.factory;

import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.RegularUser;
import za.ac.cput.util.Helper;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RecurringTransactionFactory {
    public static RecurringTransaction createRecurringTransaction(String recurrenceType, LocalDate nextExecution, BigDecimal amount, String description, Category category, RegularUser regularUser) {
        if (Helper.isNullOrEmpty(recurrenceType) || !Helper.isValidNextExecution(nextExecution) || amount == null || category == null || regularUser == null) {
            return null;
        }
        return new RecurringTransaction.RecurringTransactionBuilder()
                .setRecurrenceType(recurrenceType)
                .setNextExecution(nextExecution)
                .setAmount(amount)
                .setDescription(description)
                .setCategory(category)
                .setRegularUser(regularUser)
                .build();
    }

}
