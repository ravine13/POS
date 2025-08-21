package com.pos.soap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store just the customer ID
    @Column(name = "customer_id", nullable = false)
    private Long customerId;

    private LocalDateTime orderDate;

    private String status;   // e.g. PENDING, PAID, SHIPPED

    private Double totalAmount;

    // One order has many items
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
