package com.pos.soap.ws;

import javax.xml.bind.annotation.*;
import java.time.LocalDateTime;

@XmlRootElement(name = "SaleRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SaleRequest", propOrder = {
        "id",
        "cashierId",
        "productId",
        "quantity",
        "totalPrice",
        "saleDate"
})
public class SaleRequest {

    private Long id;
    private Long cashierId;   // ID of the User (cashier)
    private Long productId;   // ID of the Product
    private Integer quantity;
    private Double totalPrice;
    private LocalDateTime saleDate;

    // Constructors
    public SaleRequest() {}

    public SaleRequest(Long id, Long cashierId, Long productId, Integer quantity, Double totalPrice, LocalDateTime saleDate) {
        this.id = id;
        this.cashierId = cashierId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleDate = saleDate;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCashierId() {
        return cashierId;
    }

    public void setCashierId(Long cashierId) {
        this.cashierId = cashierId;
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

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
