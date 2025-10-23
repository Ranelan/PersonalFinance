package za.ac.cput.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class RecurringTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long recurringTransactionId;

    private String recurrenceType;
    private LocalDate nextExecution;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userID")
    private RegularUser regularUser;

    protected RecurringTransaction() { }

    public RecurringTransaction(RecurringTransactionBuilder b) {
        this.recurringTransactionId = b.recurringTransactionId;
        this.recurrenceType = b.recurrenceType;
        this.nextExecution = b.nextExecution;
        this.amount = b.amount;
        this.description = b.description;
        this.category = b.category;
        this.regularUser = b.regularUser;
    }

    public Long getRecurringTransactionId() { return recurringTransactionId; }
    public String getRecurrenceType() { return recurrenceType; }
    public LocalDate getNextExecution() { return nextExecution; }
    public BigDecimal getAmount() { return amount; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public RegularUser getRegularUser() { return regularUser; }

    @Override
    public String toString() {
        return "RecurringTransaction{" +
                "recurringTransactionId=" + recurringTransactionId +
                ", recurrenceType='" + recurrenceType + '\'' +
                ", nextExecution=" + nextExecution +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category=" + (category != null ? category.getCategoryId() : null) +
                '}';
    }

    public static class RecurringTransactionBuilder {
        private Long recurringTransactionId;
        private String recurrenceType;
        private LocalDate nextExecution;
        private BigDecimal amount;
        private String description;
        private Category category;
        private RegularUser regularUser;

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
        public RecurringTransactionBuilder setAmount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }
        public RecurringTransactionBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        public RecurringTransactionBuilder setCategory(Category category) {
            this.category = category;
            return this;
        }
        public RecurringTransactionBuilder setRegularUser(RegularUser regularUser) {
            this.regularUser = regularUser;
            return this;
        }
        public RecurringTransaction build() {
            return new RecurringTransaction(this);
        }
    }
}