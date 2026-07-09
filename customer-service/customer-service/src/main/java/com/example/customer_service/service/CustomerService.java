package com.example.customer_service.service;



import com.example.customer_service.dto.*;

public interface CustomerService {

    ApiResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse searchCustomer(String accountNumber);

    BalanceResponse getBalance(String accountNumber);
    ApiResponse deposit(BalanceRequest request);
    ApiResponse withdraw(BalanceRequest request);

}