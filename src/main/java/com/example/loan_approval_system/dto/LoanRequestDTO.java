package com.example.loan_approval_system.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class LoanRequestDTO {

    @NotNull(message = "公司ID不能為空")
    private Long companyId;

    @Positive(message = "貸款金額必須大於零")
    private double loanAmount;
}
