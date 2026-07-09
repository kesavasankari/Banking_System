package com.example.transactional_service.service;

import com.example.transactional_service.dto.*;
import com.example.transactional_service.entity.Transaction;
import com.example.transactional_service.entity.TransactionStatus;
import com.example.transactional_service.entity.TransactionType;
import com.example.transactional_service.feign.BalanceRequest;
import com.example.transactional_service.feign.CustomerClient;
import com.example.transactional_service.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerClient customerClient;

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  CustomerClient customerClient) {
        this.transactionRepository = transactionRepository;
        this.customerClient = customerClient;
    }

    @Override
    public ApiResponse deposit(DepositRequest request) {

        customerClient.deposit(
                new BalanceRequest(
                        request.getAccountNumber(),
                        request.getAmount()
                )
        );

        Transaction transaction = Transaction.builder()
                .accountNumber(request.getAccountNumber())
                .transactionType(TransactionType.DEPOSIT)
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.SUCCESS)
                .remarks("Cash Deposit")
                .build();

        transactionRepository.save(transaction);

        return new ApiResponse("Amount Deposited Successfully");
    }

    @Override
    public ApiResponse withdraw(WithdrawRequest request) {

        customerClient.withdraw(
                new BalanceRequest(
                        request.getAccountNumber(),
                        request.getAmount()
                )
        );

        Transaction transaction = Transaction.builder()
                .accountNumber(request.getAccountNumber())
                .transactionType(TransactionType.WITHDRAW)
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.SUCCESS)
                .remarks("Cash Withdrawal")
                .build();

        transactionRepository.save(transaction);

        return new ApiResponse("Amount Withdrawn Successfully");
    }

    @Override
    public ApiResponse transfer(TransferRequest request) {

        // Withdraw from sender
        customerClient.withdraw(
                new BalanceRequest(
                        request.getFromAccount(),
                        request.getAmount()
                )
        );

        // Deposit to receiver
        customerClient.deposit(
                new BalanceRequest(
                        request.getToAccount(),
                        request.getAmount()
                )
        );

        // Sender transaction
        Transaction debit = Transaction.builder()
                .accountNumber(request.getFromAccount())
                .transactionType(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.SUCCESS)
                .remarks("Transferred to " + request.getToAccount())
                .build();

        // Receiver transaction
        Transaction credit = Transaction.builder()
                .accountNumber(request.getToAccount())
                .transactionType(TransactionType.TRANSFER)
                .amount(request.getAmount())
                .transactionDate(LocalDateTime.now())
                .status(TransactionStatus.SUCCESS)
                .remarks("Received from " + request.getFromAccount())
                .build();

        transactionRepository.save(debit);
        transactionRepository.save(credit);

        return new ApiResponse("Fund Transfer Successful");
    }

    @Override
    public List<TransactionResponse> getTransactionHistory(String accountNumber) {

        return transactionRepository.findByAccountNumber(accountNumber)
                .stream()
                .map(transaction -> new TransactionResponse(
                        transaction.getTransactionId(),
                        transaction.getAccountNumber(),
                        transaction.getTransactionType().name(),
                        transaction.getAmount(),
                        transaction.getTransactionDate(),
                        transaction.getStatus().name()
                ))
                .collect(Collectors.toList());
    }
}