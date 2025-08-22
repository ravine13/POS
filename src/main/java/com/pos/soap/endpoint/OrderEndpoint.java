package com.pos.soap.endpoint;

import com.pos.soap.model.Order;
import com.pos.soap.model.Customer; // ✅ Added import for Customer
import com.pos.soap.service.OrderService;
import com.pos.soap.service.CustomerService; // ✅ Added import for CustomerService
import com.pos.soap.ws.OrderRequest;
import com.pos.soap.ws.OrderResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class OrderEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final OrderService orderService;
    private final CustomerService customerService; // ✅ Added CustomerService dependency

    // ✅ Updated constructor to inject CustomerService as well
    public OrderEndpoint(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    // Get all orders
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllOrdersRequest")
    @ResponsePayload
    public OrderResponse getAllOrders(@RequestPayload OrderRequest request) {
        List<Order> orders = orderService.getAllOrders();
        OrderResponse response = new OrderResponse();
        response.getOrders().addAll(orders);
        return response;
    }

    // Get order by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrderByIdRequest")
    @ResponsePayload
    public OrderResponse getOrderById(@RequestPayload OrderRequest request) {
        OrderResponse response = new OrderResponse();
        orderService.getOrderById(request.getId())
                .ifPresent(response::addOrder);
        return response;
    }

    // Get orders by customer ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrdersByCustomerIdRequest")
    @ResponsePayload
    public OrderResponse getOrdersByCustomerId(@RequestPayload OrderRequest request) {
        List<Order> orders = orderService.getOrdersByCustomerId(request.getCustomerId());
        OrderResponse response = new OrderResponse();
        response.getOrders().addAll(orders);
        return response;
    }

    // Create or update order
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveOrderRequest")
    @ResponsePayload
    public OrderResponse saveOrder(@RequestPayload OrderRequest request) {
        Order order = new Order();
        order.setId(request.getId());

        // ✅ CHANGED: Fetch customer from DB and set it
        Customer customer = customerService.getCustomerById(request.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        order.setCustomer(customer);

        order.setOrderDate(request.getOrderDate());
        order.setStatus(request.getStatus());

        Order savedOrder = orderService.saveOrder(order);

        OrderResponse response = new OrderResponse();
        response.addOrder(savedOrder);
        return response;
    }

    // Delete order
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteOrderRequest")
    @ResponsePayload
    public OrderResponse deleteOrder(@RequestPayload OrderRequest request) {
        orderService.deleteOrder(request.getId());
        return new OrderResponse(); // empty response
    }
}
