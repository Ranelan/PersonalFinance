package za.ac.cput.factory;

import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class RecurringTransactionFactory {
    public static RecurringTransaction createRecurringTransaction(String recurrenceType, LocalDate nextExecution){
        if (Helper.isNullOrEmpty(recurrenceType) ||
        Helper.isNullOrEmpty(String.valueOf(nextExecution))){
            return null;
        }

        return new RecurringTransaction.RecurringTransactionBuilder()
                .setRecurrenceType(recurrenceType)
                .setNextExecution(nextExecution)
                .build();
    }
}
