package com.pos.soap.repository;

import com.pos.soap.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    // Find customer by email
    Optional<Customer> findByEmail(String email);

    // Find customer by phone
    Optional<Customer> findByPhone(String phone);

    // Find customers by last name (could be multiple)
    List<Customer> findByLastName(String lastName);
}
