package com.example.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateCustomerRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid Email")
    @NotBlank(message = "Email is required")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must contain 10 digits")
    private String mobile;

    @NotBlank(message = "Account Type is required")
    private String accountType;

    @NotBlank(message = "Bank Name is required")
    private String bankName;

    @NotBlank(message = "Branch Name is required")
    private String branchName;

    @NotBlank(message = "IFSC Code is required")
    private String ifscCode;
}