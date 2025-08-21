package com.pos.soap.ws;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlRootElement(name = "OrderRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderRequest", propOrder = {
        "id",
        "customerId",
        "orderDate",
        "status"
})
public class OrderRequest {

    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status;

    // Constructors
    public OrderRequest() {}

    public OrderRequest(Long id, Long customerId, LocalDateTime orderDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
