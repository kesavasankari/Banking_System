package com.example.customer_service.Repository;

import com.example.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAccountNumber(String accountNumber);

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByAccountNumber(String accountNumber);

    // Returns customer with highest account number
    Optional<Customer> findTopByOrderByAccountNumberDesc();
}