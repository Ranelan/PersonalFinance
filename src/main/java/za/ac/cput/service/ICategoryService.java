package za.ac.cput.service;

import za.ac.cput.domain.Category;

import java.util.List;

public interface ICategoryService extends IService<Category,Long>{
    List<Category> findByName(String name);
    List<Category> findByType(String type);
    List<Category> findAll();
}
