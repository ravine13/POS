package com.pos.soap.endpoint;

import com.pos.soap.model.Order;
import com.pos.soap.model.Customer;
import com.pos.soap.service.OrderService;
import com.pos.soap.service.CustomerService;
import com.pos.soap.ws.*;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class OrderEndpoint {

    private static final String NAMESPACE_URI = "http://www.pos.com/soap/order";

    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderEndpoint(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    // ===== Mappers =====
    private OrderType toWs(Order e) {
        if (e == null) return null;
        OrderType o = new OrderType();
        o.setId(e.getId());
        o.setCustomerId(e.getCustomer().getId());
        o.setOrderDate(e.getOrderDate()); // ensure e.getOrderDate() is XMLGregorianCalendar or map accordingly
        o.setStatus(e.getStatus());
        // TODO: map items if you persist them (iterate and add to o.getItems().getItem())
        return o;
    }

    private Order toEntity(OrderType r) {
        if (r == null) return null;
        Order o = new Order();
        o.setId(r.getId());
        // customer fetched in save method using r.getCustomerId()
        o.setOrderDate(r.getOrderDate());
        o.setStatus(r.getStatus());
        return o;
    }

    // Get all orders
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllOrdersRequest")
    @ResponsePayload
    public GetAllOrdersResponse getAllOrders(@RequestPayload GetAllOrdersRequest request) {
        List<Order> orders = orderService.getAllOrders();
        GetAllOrdersResponse response = new GetAllOrdersResponse();
        orders.stream().map(this::toWs).forEach(response.getOrder()::add);
        return response;
    }

    // Get order by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrderByIdRequest")
    @ResponsePayload
    public GetOrderByIdResponse getOrderById(@RequestPayload GetOrderByIdRequest request) {
        GetOrderByIdResponse response = new GetOrderByIdResponse();
        orderService.getOrderById(request.getId())
                .map(this::toWs)
                .ifPresent(o -> response.getOrder().add(o));
        return response;
    }

    // Get orders by customer ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrdersByCustomerIdRequest")
    @ResponsePayload
    public GetOrdersByCustomerIdResponse getOrdersByCustomerId(@RequestPayload GetOrdersByCustomerIdRequest request) {
        List<Order> orders = orderService.getOrdersByCustomerId(request.getCustomerId());
        GetOrdersByCustomerIdResponse response = new GetOrdersByCustomerIdResponse();
        orders.stream().map(this::toWs).forEach(response.getOrder()::add);
        return response;
    }

    // Create or update order
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveOrderRequest")
    @ResponsePayload
    public SaveOrderResponse saveOrder(@RequestPayload SaveOrderRequest request) {
        OrderType payload = request; // SaveOrderRequest is typed as OrderType in the XSD
        Order toSave = toEntity(payload);

        Customer customer = customerService.getCustomerById(payload.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        toSave.setCustomer(customer);

        Order saved = orderService.saveOrder(toSave);

        SaveOrderResponse response = new SaveOrderResponse();
        response.getOrder().add(this::toWs.apply(saved)); // or response.getOrder().add(toWs(saved));
        response.setStatus("SUCCESS");
        response.setMessage("Order saved successfully");
        return response;
    }

    // Delete order
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteOrderRequest")
    @ResponsePayload
    public DeleteOrderResponse deleteOrder(@RequestPayload DeleteOrderRequest request) {
        orderService.deleteOrder(request.getId());
        DeleteOrderResponse response = new DeleteOrderResponse();
        response.setStatus("SUCCESS");
        response.setMessage("Order deleted successfully");
        return response;
    }
}
