package za.ac.cput.factory;

import za.ac.cput.domain.RecurringTransaction;
import za.ac.cput.util.Helper;

import java.time.LocalDate;

public class RecurringTransactionFactory {
    public static RecurringTransaction createRecurringTransaction(String reccuranceType, LocalDate MextExecution){
        if (Helper.isNullOrEmpty(reccuranceType) ||
        Helper.isNullOrEmpty(String.valueOf(MextExecution))){
            return null;
        }

        return new RecurringTransaction.RecurringTransactionBuilder()
                .setReccuranceType(reccuranceType)
                .setMextExecution(MextExecution)
                .build();
    }
}
