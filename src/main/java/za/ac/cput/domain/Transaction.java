/* Transaction.java
   Transaction POJO class
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 11 May 2025 */

package za.ac.cput.domain;

import java.time.LocalDate;

public class Transaction {
    private Long transactionId;
    private double amount;
    private LocalDate date;
    private String description;
    private String type;

    public Transaction() {
    }

    public Transaction(Long transactionId, double amount, LocalDate date,
                       String description, String type) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
    }

    public Transaction(TransactionBuilder builder) {
        this.transactionId = builder.transactionId;
        this.amount = builder.amount;
        this.date = builder.date;
        this.description = builder.description;
        this.type = builder.type;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public static class TransactionBuilder {
        private Long transactionId;
        private double amount;
        private LocalDate date;
        private String description;
        private String type;

        public TransactionBuilder setTransactionId(Long transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionBuilder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionBuilder setDate(LocalDate date) {
            this.date = date;
            return this;
        }

        public TransactionBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public TransactionBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public TransactionBuilder copy(Transaction transaction) {
            this.transactionId = transaction.transactionId;
            this.amount = transaction.amount;
            this.date = transaction.date;
            this.description = transaction.description;
            this.type = transaction.type;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}