package za.ac.cput.factory;

import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.domain.User;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class RecurringTransactionFactory {
    public static RecurringTransaction createRecurringTransaction(String recurrenceType, LocalDate nextExecution, User user){
        if (Helper.isNullOrEmpty(recurrenceType) || !Helper.isValidNextExecution(nextExecution) || user == null) {
            return null;
        }
        return new RecurringTransaction.RecurringTransactionBuilder()
                .setRecurrenceType(recurrenceType)
                .setNextExecution(nextExecution)
                .setUser(user)
                .build();
    }

}
