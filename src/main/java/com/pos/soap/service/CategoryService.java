package com.pos.soap.service;

import com.pos.soap.model.Category;
import com.pos.soap.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // Get category by ID
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    // Create or update a category
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // Delete a category
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
