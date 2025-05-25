/* RecurringTransaction.java
     Recurring Transaction POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */


package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recurringTransactionId;
    private String recurrenceType;
    private LocalDate nextExecution;

    protected RecurringTransaction() {
    }

    public RecurringTransaction(RecurringTransactionBuilder recurringTransactionBuilder) {
        this.recurringTransactionId = recurringTransactionBuilder.recurringTransactionId;
        this.recurrenceType = recurringTransactionBuilder.recurrenceType;
        this.nextExecution = recurringTransactionBuilder.nextExecution;
    }

    public Long getRecurringTransactionId() {
        return recurringTransactionId;
    }

    public String getRecurrenceType() {
        return recurrenceType;
    }

    public LocalDate getNextExecution() {
        return nextExecution;
    }

    @Override
    public String toString() {
        return "RecurringTransaction{" +
                "recurringTransactionId=" + recurringTransactionId +
                ", recurrenceType='" + recurrenceType + '\'' +
                ", nextExecution=" + nextExecution +
                '}';
    }

    public static class RecurringTransactionBuilder {
        private Long recurringTransactionId;
        private String recurrenceType;
        private LocalDate nextExecution;

        public RecurringTransactionBuilder setRecurringTransactionId(Long recurringTransactionId) {
            this.recurringTransactionId = recurringTransactionId;
            return this;
        }

        public RecurringTransactionBuilder setRecurrenceType(String recurrenceType) {
            this.recurrenceType = recurrenceType;
            return this;
        }

        public RecurringTransactionBuilder setNextExecution(LocalDate nextExecution) {
            nextExecution = nextExecution;
            return this;
        }

        public RecurringTransactionBuilder copy(RecurringTransaction recurringTransaction) {
            this.recurringTransactionId = recurringTransaction.recurringTransactionId;
            this.recurrenceType = recurringTransaction.recurrenceType;
            this.nextExecution = recurringTransaction.nextExecution;
            return this;
        }

        public RecurringTransaction build() {
            return new RecurringTransaction(this);
        }
    }
}
