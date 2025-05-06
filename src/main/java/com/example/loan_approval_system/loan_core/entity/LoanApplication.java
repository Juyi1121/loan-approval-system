package com.example.loan_approval_system.loan_core.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "loan_application")
public class LoanApplication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 多對一 – 公司 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    /* 申請人帳號（email 或 username）*/
    private String applicant;

    private Integer term;             // 月份
    private Double  loanAmount;
    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.PENDING;

    private String riskStatus;        // Low / Medium / High

    /* ==== Getter / Setter ==== */
    public Long getId() { return id; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public String getApplicant() { return applicant; }
    public void setApplicant(String applicant) { this.applicant = applicant; }

    public Integer getTerm() { return term; }
    public void setTerm(Integer term) { this.term = term; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

    public String getRiskStatus() { return riskStatus; }
    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }
}