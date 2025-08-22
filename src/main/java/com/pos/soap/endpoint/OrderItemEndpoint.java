package com.pos.soap.endpoint;

import com.pos.soap.model.Order;
import com.pos.soap.model.OrderItem;
import com.pos.soap.model.Product;
import com.pos.soap.service.OrderItemService;
import com.pos.soap.service.OrderService;   // ✅ Added
import com.pos.soap.service.ProductService; // ✅ Added
import com.pos.soap.ws.OrderItemRequest;
import com.pos.soap.ws.OrderItemResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class OrderItemEndpoint {

    private static final String NAMESPACE_URI = "http://pos.com/soap/ws";

    private final OrderItemService orderItemService;
    private final OrderService orderService;     // ✅ Added
    private final ProductService productService; // ✅ Added

    // ✅ Updated constructor to inject new services
    public OrderItemEndpoint(OrderItemService orderItemService,
                             OrderService orderService,
                             ProductService productService) {
        this.orderItemService = orderItemService;
        this.orderService = orderService;
        this.productService = productService;
    }

    // Get all order items
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllOrderItemsRequest")
    @ResponsePayload
    public OrderItemResponse getAllOrderItems(@RequestPayload OrderItemRequest request) {
        List<OrderItem> items = orderItemService.getAllOrderItems();
        OrderItemResponse response = new OrderItemResponse();
        response.getOrderItems().addAll(items);
        return response;
    }

    // Get order item by ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrderItemByIdRequest")
    @ResponsePayload
    public OrderItemResponse getOrderItemById(@RequestPayload OrderItemRequest request) {
        OrderItemResponse response = new OrderItemResponse();
        orderItemService.getOrderItemById(request.getId())
                .ifPresent(response::addOrderItem);
        return response;
    }

    // Get order items by order ID
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetOrderItemsByOrderIdRequest")
    @ResponsePayload
    public OrderItemResponse getOrderItemsByOrderId(@RequestPayload OrderItemRequest request) {
        List<OrderItem> items = orderItemService.getOrderItemsByOrderId(request.getOrderId());
        OrderItemResponse response = new OrderItemResponse();
        response.getOrderItems().addAll(items);
        return response;
    }

    // Create or update order item
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SaveOrderItemRequest")
    @ResponsePayload
    public OrderItemResponse saveOrderItem(@RequestPayload OrderItemRequest request) {
        OrderItem item = new OrderItem();
        item.setId(request.getId());

        // ✅ Instead of setting orderId manually → fetch the full Order entity
        Order order = orderService.getOrderById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        item.setOrder(order); // ✅ Correct association

        // ✅ Instead of setting productId manually → fetch the full Product entity
        Product product = productService.getProductById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        item.setProduct(product); // ✅ Correct association

        item.setQuantity(request.getQuantity());
        item.setPrice(request.getPrice());

        OrderItem savedItem = orderItemService.saveOrderItem(item);

        OrderItemResponse response = new OrderItemResponse();
        response.addOrderItem(savedItem);
        return response;
    }

    // Delete order item
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteOrderItemRequest")
    @ResponsePayload
    public OrderItemResponse deleteOrderItem(@RequestPayload OrderItemRequest request) {
        orderItemService.deleteOrderItem(request.getId());
        return new OrderItemResponse(); // empty response
    }
}
