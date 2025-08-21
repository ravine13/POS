package com.pos.soap.service;

import com.pos.soap.model.OrderItem;
import com.pos.soap.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    // Get all order items
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Get order item by ID
    public Optional<OrderItem> getOrderItemById(Long id) {
        return orderItemRepository.findById(id);
    }

    // Get order items by order ID
    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }

    // Get order items by product ID
    public List<OrderItem> getOrderItemsByProductId(Long productId) {
        return orderItemRepository.findByProductId(productId);
    }

    // Create or update an order item
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    // Delete an order item
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }

    // Optional: Throw exception if order item not found by ID
    public OrderItem getOrderItemByIdOrThrow(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id " + id));
    }
}
