package com.pos.soap.ws;

import com.pos.soap.model.Product;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "ProductResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductResponse", propOrder = {
        "products"
})
public class ProductResponse {

    @XmlElement(name = "product")
    private List<Product> products = new ArrayList<>();

    // Constructors
    public ProductResponse() {}

    public ProductResponse(List<Product> products) {
        this.products = products;
    }

    // Getter and setter
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // Add a single product
    public void addProduct(Product product) {
        this.products.add(product);
    }
}
