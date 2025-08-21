package com.pos.soap.ws;

import com.pos.soap.model.Category;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CategoryRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryRequest", propOrder = {
        "id",
        "name"
})
public class CategoryRequest {

    private Long id;
    private String name;

    // Constructors
    public CategoryRequest() {}

    public CategoryRequest(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return Category;
    }
}
