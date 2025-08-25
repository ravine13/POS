package com.pos.soap.ws;

import com.pos.soap.model.Category;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "CategoryResponse", namespace = "http://pos.com/soap/categories")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryResponse", propOrder = {
        "categories"
}, namespace = "http://pos.com/soap/categories")
public class CategoryResponse {

    @XmlElement(name = "category")
    private List<Category> categories = new ArrayList<>();

    public CategoryResponse() {}

    public CategoryResponse(List<Category> categories) {
        this.categories = categories;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }
}
