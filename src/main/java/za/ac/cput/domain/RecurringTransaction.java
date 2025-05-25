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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recurringTransactionId;
    private String reccuranceType;
    private LocalDate MextExecution;

    public RecurringTransaction() {
    }

    public RecurringTransaction(RecurringTransactionBuilder recurringTransactionBuilder) {
        this.recurringTransactionId = recurringTransactionBuilder.recurringTransactionId;
        this.reccuranceType = recurringTransactionBuilder.reccuranceType;
        this.MextExecution = recurringTransactionBuilder.MextExecution;
    }

    public Long getRecurringTransactionId() {
        return recurringTransactionId;
    }

    public String getReccuranceType() {
        return reccuranceType;
    }

    public LocalDate getMextExecution() {
        return MextExecution;
    }

    @Override
    public String toString() {
        return "RecurringTransaction{" +
                "recurringTransactionId=" + recurringTransactionId +
                ", reccuranceType='" + reccuranceType + '\'' +
                ", MextExecution=" + MextExecution +
                '}';
    }

    public static class RecurringTransactionBuilder {
        private Long recurringTransactionId;
        private String reccuranceType;
        private LocalDate MextExecution;

        public RecurringTransactionBuilder setRecurringTransactionId(Long recurringTransactionId) {
            this.recurringTransactionId = recurringTransactionId;
            return this;
        }

        public RecurringTransactionBuilder setReccuranceType(String reccuranceType) {
            this.reccuranceType = reccuranceType;
            return this;
        }

        public RecurringTransactionBuilder setMextExecution(LocalDate mextExecution) {
            MextExecution = mextExecution;
            return this;
        }

        public RecurringTransactionBuilder copy(RecurringTransaction recurringTransaction) {
            this.recurringTransactionId = recurringTransaction.recurringTransactionId;
            this.reccuranceType = recurringTransaction.reccuranceType;
            this.MextExecution = recurringTransaction.MextExecution;
            return this;
        }

        public RecurringTransaction build() {
            return new RecurringTransaction(this);
        }
    }
}
