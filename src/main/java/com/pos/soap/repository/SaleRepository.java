package com.pos.soap.repository;

import com.pos.soap.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    // Find all sales for a specific customer
    List<Sale> findByCustomer_Id(Long customerId);

    // Find sales made on a specific date
    List<Sale> findBySaleDate(LocalDate saleDate);

    // Find sales within a date range
    List<Sale> findBySaleDateBetween(LocalDate startDate, LocalDate endDate);
}

