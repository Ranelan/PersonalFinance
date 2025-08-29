/* RecurringTransaction.java
     Recurring Transaction POJO class
     Author: Ranelani Engel(221813853
     Date: 11 May 2025 */


package za.ac.cput.domain;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class RecurringTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recurringTransactionId;
    private String recurrenceType;
    private LocalDate nextExecution;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private User user;

    protected RecurringTransaction() {
    }

    public RecurringTransaction(RecurringTransactionBuilder recurringTransactionBuilder) {
        this.recurringTransactionId = recurringTransactionBuilder.recurringTransactionId;
        this.recurrenceType = recurringTransactionBuilder.recurrenceType;
        this.nextExecution = recurringTransactionBuilder.nextExecution;
        this.user = recurringTransactionBuilder.user;
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

    public User getUser() {
        return user;
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
        private User user;

        public RecurringTransactionBuilder setRecurringTransactionId(Long recurringTransactionId) {
            this.recurringTransactionId = recurringTransactionId;
            return this;
        }

        public RecurringTransactionBuilder setRecurrenceType(String recurrenceType) {
            this.recurrenceType = recurrenceType;
            return this;
        }

        public RecurringTransactionBuilder setNextExecution(LocalDate nextExecution) {
            this.nextExecution = nextExecution;
            return this;
        }

        public RecurringTransactionBuilder setUser(User user) {
            this.user = user;
            return this;
        }

        public RecurringTransactionBuilder copy(RecurringTransaction recurringTransaction) {
            this.recurringTransactionId = recurringTransaction.recurringTransactionId;
            this.recurrenceType = recurringTransaction.recurrenceType;
            this.nextExecution = recurringTransaction.nextExecution;
            this.user = recurringTransaction.user;
            return this;
        }

        public RecurringTransaction build() {
            return new RecurringTransaction(this);
        }
    }
}
