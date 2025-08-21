package com.pos.soap.service;

import com.pos.soap.model.Customer;
import com.pos.soap.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    // Get all customers
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Get customer by ID
    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    // Get customer by email
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    // Get customer by phone
    public Optional<Customer> getCustomerByPhone(String phone) {
        return customerRepository.findByPhone(phone);
    }

    // Get customers by last name
    public List<Customer> getCustomersByLastName(String lastName) {
        return customerRepository.findByLastName(lastName);
    }

    // Create or update a customer
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Delete a customer
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    // Optional: Throw exception if customer not found by ID
    public Customer getCustomerByIdOrThrow(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id " + id));
    }
}
