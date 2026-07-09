package com.example.transactional_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "CUSTOMER-SERVICE")
public interface CustomerClient {

    // Search Customer
    @GetMapping("/customer/{accountNumber}")
    CustomerResponse getCustomer(
            @PathVariable String accountNumber
    );

    // Deposit Balance
    @PutMapping("/customer/deposit")
    void deposit(@RequestBody BalanceRequest request);

    // Withdraw Balance
    @PutMapping("/customer/withdraw")
    void withdraw(@RequestBody BalanceRequest request);
}