/* Transaction.java
   Transaction POJO class with relationships
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 11 May 2025
*/

package za.ac.cput.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private double amount;
    private LocalDate date;
    private String description;
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private RegularUser regularUser;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public Transaction() {
    }

    public Transaction(Long transactionId, double amount, LocalDate date,
                       String description, String type,
                       RegularUser regularUser, Category category) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.type = type;
        this.regularUser = regularUser;
        this.category = category;
    }

    public Transaction(TransactionBuilder builder) {
        this.transactionId = builder.transactionId;
        this.amount = builder.amount;
        this.date = builder.date;
        this.description = builder.description;
        this.type = builder.type;
        this.regularUser = builder.regularUser;
        this.category = builder.category;
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

    public RegularUser getRegularUser() {
        return regularUser;
    }

    public Category getCategory() {
        return category;
    }

    public static class TransactionBuilder {
        private Long transactionId;
        private double amount;
        private LocalDate date;
        private String description;
        private String type;
        private RegularUser regularUser;
        private Category category;

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

        public TransactionBuilder setRegularUser(RegularUser regularUser) {
            this.regularUser = regularUser;
            return this;
        }

        public TransactionBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }

        public TransactionBuilder copy(Transaction transaction) {
            this.transactionId = transaction.transactionId;
            this.amount = transaction.amount;
            this.date = transaction.date;
            this.description = transaction.description;
            this.type = transaction.type;
            this.regularUser = transaction.regularUser;
            this.category = transaction.category;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
