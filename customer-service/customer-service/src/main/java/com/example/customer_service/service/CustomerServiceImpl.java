package com.example.customer_service.service;

import com.example.customer_service.Repository.CustomerRepository;
import com.example.customer_service.dto.ApiResponse;
import com.example.customer_service.dto.BalanceRequest;
import com.example.customer_service.dto.BalanceResponse;
import com.example.customer_service.dto.CreateCustomerRequest;
import com.example.customer_service.dto.CustomerResponse;
import com.example.customer_service.entity.Customer;
import com.example.customer_service.exception.CustomerNotFoundException;
import com.example.customer_service.exception.EmailAlreadyExistsException;
import com.example.customer_service.exception.InsufficientBalanceException;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public ApiResponse createCustomer(CreateCustomerRequest request) {

        // Check if email already exists
        if (customerRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        // Generate next account number
        String accountNumber = generateAccountNumber();

        // Create Customer
        Customer customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .mobile(request.getMobile())
                .accountNumber(accountNumber)
                .accountType(request.getAccountType())
                .bankName(request.getBankName())
                .branchName(request.getBranchName())
                .ifscCode(request.getIfscCode())
                .balance(0.0)
                .build();

        // Save Customer
        customerRepository.save(customer);

        return new ApiResponse("Customer Created Successfully");
    }

    @Override
    public CustomerResponse searchCustomer(String accountNumber) {

        Customer customer = customerRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));

        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getName(),
                customer.getEmail(),
                customer.getMobile(),
                customer.getAccountNumber(),
                customer.getAccountType(),
                customer.getBankName(),
                customer.getBranchName(),
                customer.getIfscCode(),
                customer.getBalance()
        );
    }

    @Override
    public BalanceResponse getBalance(String accountNumber) {

        Customer customer = customerRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));

        return new BalanceResponse(
                customer.getAccountNumber(),
                customer.getBalance()
        );
    }

    @Override
    public ApiResponse deposit(BalanceRequest request) {

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Customer customer = customerRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));

        customer.setBalance(customer.getBalance() + request.getAmount());

        customerRepository.save(customer);

        return new ApiResponse("Amount Deposited Successfully");
    }

    @Override
    public ApiResponse withdraw(BalanceRequest request) {

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Customer customer = customerRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not Found"));

        if (customer.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException("Insufficient Balance");
        }

        customer.setBalance(customer.getBalance() - request.getAmount());

        customerRepository.save(customer);

        return new ApiResponse("Amount Withdrawn Successfully");
    }

    /**
     * Generates sequential account numbers.
     * First account : 100000000001
     * Next account  : 100000000002
     * Next account  : 100000000003
     */
    private synchronized String generateAccountNumber() {

        return customerRepository.findTopByOrderByAccountNumberDesc()
                .map(customer -> {
                    long lastAccountNumber = Long.parseLong(customer.getAccountNumber());
                    return String.valueOf(lastAccountNumber + 1);
                })
                .orElse("100000000001");
    }
}