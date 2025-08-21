package com.pos.soap.repository;

import com.pos.soap.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Find all orders for a specific customer
    List<Order> findByCustomerId(Long customerId);

    // Find all orders by status (e.g., PENDING, COMPLETED, CANCELLED)
    List<Order> findByStatus(String status);

    // Find all orders placed between two dates
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
