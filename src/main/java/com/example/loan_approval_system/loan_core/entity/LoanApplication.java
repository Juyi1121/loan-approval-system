package com.example.loan_approval_system.loan_core.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "loan_application")
public class LoanApplication {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* 與公司多對一關聯 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private Double loanAmount;
    private LocalDateTime applicationDate;

    @Enumerated(EnumType.STRING)
    private LoanStatus status = LoanStatus.PENDING;   // 預設待審核

    private String riskStatus;  // Low / Medium / High

    /* ===== Getter / Setter ===== */
    public Long getId() { return id; }

    public Company getCompany() { return company; }
    public void setCompany(Company company) { this.company = company; }

    public Double getLoanAmount() { return loanAmount; }
    public void setLoanAmount(Double loanAmount) { this.loanAmount = loanAmount; }

    public LocalDateTime getApplicationDate() { return applicationDate; }
    public void setApplicationDate(LocalDateTime applicationDate) { this.applicationDate = applicationDate; }

    public LoanStatus getStatus() { return status; }
    public void setStatus(LoanStatus status) { this.status = status; }

    public String getRiskStatus() { return riskStatus; }
    public void setRiskStatus(String riskStatus) { this.riskStatus = riskStatus; }
}
