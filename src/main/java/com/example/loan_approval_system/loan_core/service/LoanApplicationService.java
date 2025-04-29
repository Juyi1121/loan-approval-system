package com.example.loan_approval_system.loan_core.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.loan_approval_system.loan_core.entity.*;
import com.example.loan_approval_system.loan_core.repository.*;

@Service
public class LoanApplicationService {

    private final LoanApplicationRepository loanRepo;
    private final CompanyRepository companyRepo;

    public LoanApplicationService(LoanApplicationRepository loanRepo,
                                  CompanyRepository companyRepo) {
        this.loanRepo = loanRepo;
        this.companyRepo = companyRepo;
    }

    /* ========= 申請貸款 ========= */
    @Transactional
    public LoanApplication applyForLoan(Long companyId, double loanAmount) {

        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("公司不存在"));

        LoanApplication app = new LoanApplication();
        app.setCompany(company);
        app.setLoanAmount(loanAmount);
        app.setApplicationDate(LocalDateTime.now());
        app.setRiskStatus(evaluateRisk(company));        // Low / Medium / High
        app.setStatus(LoanStatus.PENDING);

        return loanRepo.save(app);
    }

    /* ========= 查詢待審核 ========= */
    public List<LoanApplication> findPending() {
        return loanRepo.findByStatus(LoanStatus.PENDING);
    }

    /* ========= 核准 ========= */
    @Transactional
    public void approve(Long id) {
        LoanApplication app = loanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        app.setStatus(LoanStatus.APPROVED);
    }

    /* ========= 拒絕 ========= */
    @Transactional
    public void reject(Long id) {
        LoanApplication app = loanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        app.setStatus(LoanStatus.REJECTED);
    }

    /* ========= 風險評估 ========= */
    private String evaluateRisk(Company c) {
        double dr = c.getDebtRatio();
        double cs = c.getCreditScore();

        if (dr > 0.6 || cs < 600)         return "High Risk";
        if (dr > 0.3 || cs <= 700)        return "Medium Risk";
        return "Low Risk";
    }
}
