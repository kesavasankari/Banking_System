package com.example.transactional_service.service;


import com.example.transactional_service.dto.ApiResponse;
import com.example.transactional_service.dto.DepositRequest;
import com.example.transactional_service.dto.TransactionResponse;
import com.example.transactional_service.dto.TransferRequest;
import com.example.transactional_service.dto.WithdrawRequest;

import java.util.List;

public interface TransactionService {

    ApiResponse deposit(DepositRequest request);

    ApiResponse withdraw(WithdrawRequest request);

    ApiResponse transfer(TransferRequest request);

    List<TransactionResponse> getTransactionHistory(String accountNumber);

}