package com.example.loan_approval_system.loan_core.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class LoanApplicationDTO {
    @NotNull private Long companyId;
    @NotNull @Min(1) private BigDecimal amount;
    @NotNull @Min(1) private Integer term;   // 月數
    
}
