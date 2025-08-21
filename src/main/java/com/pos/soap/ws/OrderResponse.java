package com.pos.soap.ws;

import com.pos.soap.model.Order;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "OrderResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderResponse", propOrder = {
        "orders"
})
public class OrderResponse {

    @XmlElement(name = "order")
    private List<Order> orders = new ArrayList<>();

    // Constructors
    public OrderResponse() {}

    public OrderResponse(List<Order> orders) {
        this.orders = orders;
    }

    // Getter and Setter
    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    // Add a single order
    public void addOrder(Order order) {
        this.orders.add(order);
    }
}
