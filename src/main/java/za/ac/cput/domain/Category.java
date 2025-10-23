/*
 * Category.java
 * Category Entity Class
 * Author: Lebuhang Nyanyantsi (222184353)
 * Date: 11 May 2025
 */

package za.ac.cput.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;
    private String type;

    @OneToOne(mappedBy = "category")
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "regular_user_id")
    private RegularUser regularUser;

    public Category() {}

    public Category(Long categoryId, String name, String type, Transaction transaction, RegularUser regularUser) {
        this.categoryId = categoryId;
        this.name = name;
        this.type = type;
        this.transaction = transaction;
        this.regularUser = regularUser;
    }

    public Category(CategoryBuilder builder) {
        this.categoryId = builder.categoryId;
        this.name = builder.name;
        this.type = builder.type;
        this.transaction = builder.transaction;
        this.regularUser = builder.regularUser;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public RegularUser getRegularUser() {
        return regularUser;
    }

    public void setRegularUser(RegularUser regularUser) {
        this.regularUser = regularUser;
    }

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", transaction=" + transaction +
                ", regularUser=" + regularUser +
                '}';
    }

    public static class CategoryBuilder {
        private Long categoryId;
        private String name;
        private String type;
        private Transaction transaction;
        private RegularUser regularUser;

        public CategoryBuilder setId(Long categoryId) {
            this.categoryId = categoryId;
            return this;
        }

        public CategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public CategoryBuilder setTransaction(Transaction transaction) {
            this.transaction = transaction;
            return this;
        }

        public CategoryBuilder setRegularUser(RegularUser regularUser) {
            this.regularUser = regularUser;
            return this;
        }

        public CategoryBuilder copy(Category category) {
            this.categoryId = category.categoryId;
            this.name = category.name;
            this.type = category.type;
            this.transaction = category.transaction;
            this.regularUser = category.regularUser;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
