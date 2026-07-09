package com.example.transactional_service.feign;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {

    private Long customerId;

    private String name;

    private String email;

    private String mobile;

    private String accountNumber;

    private String accountType;

    private String bankName;

    private String branchName;

    private String ifscCode;

    private Double balance;
}