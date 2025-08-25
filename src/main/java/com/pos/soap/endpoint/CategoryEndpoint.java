package com.pos.soap.endpoint;

import com.pos.soap.model.Category;
import com.pos.soap.service.CategoryService;
import com.pos.soap.ws.CategoryRequest;
//import com.pos.soap.ws.AllCategoriesRequest;
import com.pos.soap.ws.CategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class CategoryEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/categories";

    @Autowired
    private CategoryService categoryService;

    // ✅ Handles <CategoryRequest>
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CategoryRequest")
    @ResponsePayload
    public CategoryResponse getCategory(@RequestPayload CategoryRequest request) {
        CategoryResponse response = new CategoryResponse();
        categoryService.getCategoryById(request.getId())
                .ifPresent(response.getCategories()::add);
        return response;
    }

    // ✅ Handles <AllCategoriesRequest>
//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AllCategoriesRequest")
//    @ResponsePayload
//    public CategoryResponse getAllCategories(@RequestPayload AllCategoriesRequest request) {
//        CategoryResponse response = new CategoryResponse();
//        List<Category> categories = categoryService.getAllCategories();
//        response.getCategories().addAll(categories);
//        return response;
//    }
}
