package com.pos.soap.service;

import com.pos.soap.model.Sale;
import com.pos.soap.repository.SaleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SaleService {

    private final SaleRepository saleRepository;

    public SaleService(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    // Get all sales
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    // Get sale by ID
    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    // Get sales by customer (user) ID
    public List<Sale> getSalesByCustomerId(Long customerId) {
        return saleRepository.findByCustomer_Id(customerId);
    }

    // Get sales by specific date
    public List<Sale> getSalesByDate(LocalDateTime saleDate) {
        return saleRepository.findBySaleDate(saleDate.toLocalDate());
    }

    // Get sales within a date range
    public List<Sale> getSalesBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return saleRepository.findBySaleDateBetween(startDate.toLocalDate(), endDate.toLocalDate());
    }

    // Create or update a sale
    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    // Delete a sale
    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    // Throw exception if sale not found
    public Sale getSaleByIdOrThrow(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale not found with id " + id));
    }

    // Safe update method
    public Sale updateSale(Long id, Sale saleDetails) {
        Sale sale = getSaleByIdOrThrow(id);
        sale.setCashierId(saleDetails.getCashierId()); // use ID now
        sale.setProductId(saleDetails.getProductId()); // use ID now
        sale.setQuantity(saleDetails.getQuantity());
        sale.setTotalPrice(saleDetails.getTotalPrice());
        sale.setSaleDate(saleDetails.getSaleDate());
        return saleRepository.save(sale);
    }
}
