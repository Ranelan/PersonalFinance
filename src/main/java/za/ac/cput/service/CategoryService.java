package za.ac.cput.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.ac.cput.domain.Category;
import za.ac.cput.domain.Transaction;
import za.ac.cput.repository.CategoryRepository;
import za.ac.cput.repository.TransactionRepository;

import java.util.List;

@Service
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category read(Long aLong) {
        return categoryRepository.findById(aLong).orElse(null);
    }



    @Override
    public Category update(Category category) {
        if(category.getCategoryId() != null && categoryRepository.existsById(category.getCategoryId())) {
            Transaction transaction = category.getTransaction();
            if(transaction != null && (transaction.getTransactionId() == null)) {
                throw new IllegalStateException("Cannot save category with transient transaction entity (null id). Save transaction first.");
            }
            return categoryRepository.save(category);
        }
        return null;
    }


    @Override
    public void delete(Long aLong) {
        if(categoryRepository.existsById(aLong)) {
            categoryRepository.deleteById(aLong);
        }
    }

    @Override
    public List<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> findByType(String type) {
        return categoryRepository.findByType(type);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

}
