package com.example.loan_approval_system.loan_core.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 前端提交的「申請貸款」表單 DTO。
 * 加入 {@code @NoArgsConstructor} 以方便 Jackson 反序列化 JSON。
 */
@Data
@NoArgsConstructor // ⭐ 新增：Jackson 需要無參數建構子
public class LoanRequestDTO {

    @NotNull(message = "公司ID不能為空")
    private Long companyId;

    @Positive(message = "貸款金額必須大於零")
    private double loanAmount;

    @NotNull(message = "期數不能為空")
    @Min(value = 1, message = "期數必須大於零")
    private Integer term;

    @NotBlank(message = "申請人不能為空")
    private String applicant;
}