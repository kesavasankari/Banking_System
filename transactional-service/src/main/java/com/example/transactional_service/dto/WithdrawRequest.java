package com.example.transactional_service.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class WithdrawRequest {

    @NotBlank(message = "Account Number is required")
    private String accountNumber;

    @Min(value = 1, message = "Amount should be greater than zero")
    private Double amount;
}