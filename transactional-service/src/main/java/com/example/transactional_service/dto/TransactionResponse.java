package com.example.transactional_service.dto;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {

    private Long transactionId;

    private String accountNumber;

    private String transactionType;

    private Double amount;

    private LocalDateTime transactionDate;

    private String status;
}