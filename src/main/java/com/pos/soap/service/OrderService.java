package com.pos.soap.service;

import com.pos.soap.model.Order;
import com.pos.soap.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Get order by ID
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    // Get orders by customer ID
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    // Get orders by status
    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    // Get orders between two dates
    public List<Order> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    // Create or update an order
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    // Delete an order
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    // Optional: Throw exception if order not found by ID
    public Order getOrderByIdOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id " + id));
    }
}
