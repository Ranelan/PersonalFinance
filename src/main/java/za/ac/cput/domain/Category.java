/*
 * Category.java
 * Category Entity Class
 * Author: Lebuhang Nyanyantsi (222184353)
 * Date: 11 May 2025
 */

package za.ac.cput.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    private String name;
    private String type;

    public Category() {
        // JPA requires a no-arg constructor
    }

    private Category(CategoryBuilder builder) {
        this.name = builder.name;
        this.type = builder.type;
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

    @Override
    public String toString() {
        return "Category{" +
                "categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public static class CategoryBuilder {
        private String name;
        private String type;

        public CategoryBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public CategoryBuilder setType(String type) {
            this.type = type;
            return this;
        }

        public CategoryBuilder copy(Category category) {
            this.name = category.name;
            this.type = category.type;
            return this;
        }

        public Category build() {
            return new Category(this);
        }
    }
}
