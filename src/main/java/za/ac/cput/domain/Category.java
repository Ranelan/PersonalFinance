/* Category.java
   Category POJO class
   Author: Lebuhang Nyanyantsi(222184353)
   Date: 11 May 2025 */

   package za.ac.cput.domain;

   public class Category {
       private Long categoryId;
       private String name;
       private String type;
   
       public Category() {
       }
   
       public Category(Long categoryId, String name, String type) {
           this.categoryId = categoryId;
           this.name = name;
           this.type = type;
       }
   
       public Category(CategoryBuilder builder) {
           this.categoryId = builder.categoryId;
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
   
       public static class CategoryBuilder {
           private Long categoryId;
           private String name;
           private String type;
   
           public CategoryBuilder setCategoryId(Long categoryId) {
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
   
           public CategoryBuilder copy(Category category) {
               this.categoryId = category.categoryId;
               this.name = category.name;
               this.type = category.type;
               return this;
           }
   
           public Category build() {
               return new Category(this);
           }
       }
   }