package za.ac.cput.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.ac.cput.domain.Category;
import za.ac.cput.service.ICategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final ICategoryService categoryService;

    @Autowired
    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Category> create(@RequestBody Category category) {
        Category newCategory = categoryService.create(category);
        if(newCategory != null) {
            return ResponseEntity.ok(newCategory);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/read/{id}")
    public ResponseEntity<Category> read(@PathVariable Long id) {
        Category readCategory = categoryService.read(id);
        if(readCategory != null) {
            return ResponseEntity.ok(readCategory);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Category> update(@RequestBody Category category) {
        Category updateCategory = categoryService.update(category);
        if(updateCategory != null) {
            return ResponseEntity.ok(updateCategory);
        } else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<Category> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/findByName/{name}")
    public ResponseEntity<List<Category>> findByName(@PathVariable String name) {
        List<Category> categories = categoryService.findByName(name);
        if(categories != null &&  !categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findByType/{type}")
    public ResponseEntity<List<Category>> findByType(@PathVariable String type) {
        List<Category> categories = categoryService.findByType(type);
        if(categories != null &&  !categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Category>> findAll() {
        List<Category> categories = categoryService.findAll();
        if(categories != null &&  !categories.isEmpty()) {
            return ResponseEntity.ok(categories);
        } else{
            return ResponseEntity.notFound().build();
        }
    }

}
