package com.pos.soap.ws;

import com.pos.soap.model.OrderItem;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "OrderItemResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderItemResponse", propOrder = {
        "orderItems"
})
public class OrderItemResponse {

    @XmlElement(name = "orderItem")
    private List<OrderItem> orderItems = new ArrayList<>();

    // Constructors
    public OrderItemResponse() {}

    public OrderItemResponse(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // Getter and Setter
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // Add a single order item
    public void addOrderItem(OrderItem item) {
        this.orderItems.add(item);
    }
}
