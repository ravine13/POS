package com.pos.soap.repository;

import com.pos.soap.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Find products by category
    List<Product> findByCategoryId(Long categoryId);

    // Search products by name (case-insensitive)
    List<Product> findByNameContainingIgnoreCase(String name);

    // Find products within a price range
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Find products with stock less than a given threshold
    List<Product> findByStockQuantityLessThan(Integer quantity);
}
