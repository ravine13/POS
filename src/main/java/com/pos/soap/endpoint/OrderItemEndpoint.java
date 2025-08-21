package com.pos.soap.endpoint;

import com.pos.soap.model.OrderItem;
import com.pos.soap.service.OrderItemService;
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

    public OrderItemEndpoint(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
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
        item.setOrderId(request.getOrderId());
        item.setProductId(request.getProductId());
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
