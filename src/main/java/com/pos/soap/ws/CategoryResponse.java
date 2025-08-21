package com.pos.soap.ws;

import com.pos.soap.model.Category;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "CategoryResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryResponse", propOrder = {
        "categories"
})
public class CategoryResponse {

    @XmlElement(name = "category")
    private List<Category> categories = new ArrayList<>();

    // Constructors
    public CategoryResponse() {}

    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }

    // Getter and Setter
    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    // Add a single category
    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
