package com.example.customer_service.controller;


import com.example.customer_service.dto.BalanceRequest;
import com.example.customer_service.dto.ApiResponse;
import com.example.customer_service.dto.BalanceResponse;
import com.example.customer_service.dto.CreateCustomerRequest;
import com.example.customer_service.dto.CustomerResponse;
import com.example.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create Customer
    @PostMapping("/create")
    public ApiResponse createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
        return customerService.createCustomer(request);
    }

    // Search Customer
    @GetMapping("/{accountNumber}")
    public CustomerResponse searchCustomer(@PathVariable String accountNumber) {
        return customerService.searchCustomer(accountNumber);
    }

    // Balance Inquiry
    @GetMapping("/balance/{accountNumber}")
    public BalanceResponse getBalance(@PathVariable String accountNumber) {
        return customerService.getBalance(accountNumber);
    }
    @PutMapping("/deposit")
    public ApiResponse deposit(@RequestBody BalanceRequest request) {
        return customerService.deposit(request);
    }

    @PutMapping("/withdraw")
    public ApiResponse withdraw(@RequestBody BalanceRequest request) {
        return customerService.withdraw(request);
    }
}