package com.pos.soap.endpoint;

import com.pos.soap.model.Category;
import com.pos.soap.service.CategoryService;
import com.pos.soap.ws.CategoryRequest;
import com.pos.soap.ws.CategoryResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CategoryEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final CategoryService categoryService;

    public CategoryEndpoint(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // Get all categories
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllCategoriesRequest")
    @ResponsePayload
    public CategoryResponse getAllCategories(@RequestPayload CategoryRequest request) {
        CategoryResponse response = new CategoryResponse();
        List<Category> categories = categoryService.getAllCategories();
        response.getCategories().addAll(categories);
        return response;
    }

    // Get category by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetCategoryByIdRequest")
    @ResponsePayload
    public CategoryResponse getCategoryById(@RequestPayload CategoryRequest request) {
        CategoryResponse response = new CategoryResponse();
        categoryService.getCategoryById(request.getId())
                .ifPresent(response.getCategories()::add);
        return response;
    }

    // Create or update category
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveCategoryRequest")
    @ResponsePayload
    public CategoryResponse saveCategory(@RequestPayload CategoryRequest request) {
        Category category = new Category();
        category.setId(request.getId());
        category.setName(request.getName());

        Category savedCategory = categoryService.saveCategory(category);

        CategoryResponse response = new CategoryResponse();
        response.getCategories().add(savedCategory);
        return response;
    }

    // Delete category
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteCategoryRequest")
    @ResponsePayload
    public CategoryResponse deleteCategory(@RequestPayload CategoryRequest request) {
        categoryService.deleteCategory(request.getId());
        return new CategoryResponse(); // empty response
    }
}
