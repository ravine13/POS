package com.pos.soap.ws;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "OrderItemRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderItemRequest", propOrder = {
        "id",
        "orderId",
        "productId",
        "quantity",
        "price"
})
public class OrderItemRequest {

    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private Double price;

    // Constructors
    public OrderItemRequest() {}

    public OrderItemRequest(Long id, Long orderId, Long productId, Integer quantity, Double price) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
