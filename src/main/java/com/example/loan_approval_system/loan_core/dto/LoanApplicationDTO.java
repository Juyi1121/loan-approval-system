package com.example.loan_approval_system.loan_core.dto;

import jakarta.validation.constraints.*;

public class LoanApplicationDTO {

    @NotNull(message = "公司 ID 不可為空")
    private Long companyId;

    @NotNull(message = "貸款金額不可為空")
    @Positive(message = "貸款金額必須大於 0")
    private Double loanAmount;

    @NotNull(message = "貸款期限不可為空")
    @Min(value = 1, message = "貸款期限至少要 1 個月")
    private Integer term;

    @NotBlank(message = "申請人不可為空")
    private String applicant;

    // 下面是標準 getter/setter

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant;
    }
}
