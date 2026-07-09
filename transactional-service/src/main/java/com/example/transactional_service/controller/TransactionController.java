package com.example.transactional_service.controller;
import com.example.transactional_service.dto.ApiResponse;
import com.example.transactional_service.dto.DepositRequest;
import com.example.transactional_service.dto.TransactionResponse;
import com.example.transactional_service.dto.TransferRequest;
import com.example.transactional_service.dto.WithdrawRequest;
import com.example.transactional_service.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // Deposit Money
    @PostMapping("/deposit")
    public ApiResponse deposit(@Valid @RequestBody DepositRequest request) {
        return transactionService.deposit(request);
    }

    // Withdraw Money
    @PostMapping("/withdraw")
    public ApiResponse withdraw(@Valid @RequestBody WithdrawRequest request) {
        return transactionService.withdraw(request);
    }

    // Fund Transfer
    @PostMapping("/transfer")
    public ApiResponse transfer(@Valid @RequestBody TransferRequest request) {
        return transactionService.transfer(request);
    }

    // Transaction History
    @GetMapping("/history/{accountNumber}")
    public List<TransactionResponse> getTransactionHistory(
            @PathVariable String accountNumber) {

        return transactionService.getTransactionHistory(accountNumber);
    }
}