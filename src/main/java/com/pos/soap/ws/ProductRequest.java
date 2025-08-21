package com.pos.soap.ws;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "ProductRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductRequest", propOrder = {
        "id",
        "name",
        "price",
        "quantity",
        "categoryId"
})
public class ProductRequest {

    private Long id;
    private String name;
    private Double price;
    private Integer quantity;
    private Long categoryId;  // Use categoryId instead of Category object

    // Constructors
    public ProductRequest() {}

    public ProductRequest(Long id, String name, Double price, Integer quantity, Long categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.categoryId = categoryId;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
