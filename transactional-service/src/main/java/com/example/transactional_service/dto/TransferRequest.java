package com.example.transactional_service.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TransferRequest {

    @NotBlank(message = "From Account is required")
    private String fromAccount;

    @NotBlank(message = "To Account is required")
    private String toAccount;

    @Min(value = 1, message = "Amount should be greater than zero")
    private Double amount;
}