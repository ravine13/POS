package com.pos.soap.repository;

import com.pos.soap.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by username
    Optional<User> findByUsername(String username);

    // Check if username already exists
    boolean existsByUsername(String username);

    // Get all users by role (e.g., ADMIN, CUSTOMER)
    List<User> findByRole(String role);
}
