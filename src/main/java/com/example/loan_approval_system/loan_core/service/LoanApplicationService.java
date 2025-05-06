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
    public LoanApplication applyForLoan(Long companyId,
            double loanAmount,
            int term,
            String applicant) {

        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new IllegalArgumentException("公司不存在"));

        LoanApplication app = new LoanApplication();
        app.setCompany(company);
        app.setApplicant(applicant);
        app.setLoanAmount(loanAmount);
        app.setTerm(term);
        app.setApplicationDate(LocalDateTime.now());
        app.setRiskStatus(evaluateRisk(company)); // Low / Medium / High
        app.setStatus(LoanStatus.PENDING);

        return loanRepo.save(app);
    }

    /* ========= Reviewer / Admin 查詢 ========= */
    public List<LoanApplication> findByStatus(LoanStatus status) {
        return loanRepo.findByStatusOrderByApplicationDateDesc(status);
    }

    public List<LoanApplication> findAll() {
        return loanRepo.findAllByOrderByApplicationDateDesc();
    }

    /* ========= Applicant 查詢 ========= */
    public List<LoanApplication> findByApplicant(String applicantAcct) {
        return loanRepo.findByApplicantOrderByApplicationDateDesc(applicantAcct);
    }

    /* ========= 審核 ========= */
    @Transactional
    public void approve(Long id) {
        LoanApplication app = loanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        app.setStatus(LoanStatus.APPROVED);
        loanRepo.save(app); // ← 確保 UPDATE
    }

    @Transactional
    public void reject(Long id) {
        LoanApplication app = loanRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
        app.setStatus(LoanStatus.REJECTED);
        loanRepo.save(app); // ← 確保 UPDATE
    }

    /* ========= 風險評估 (placeholder) ========= */
    private String evaluateRisk(Company c) {
        double dr = c.getDebtRatio();
        double cs = c.getCreditScore();
        if (dr > 0.6 || cs < 600)
            return "High Risk";
        if (dr > 0.3 || cs <= 700)
            return "Medium Risk";
        return "Low Risk";
    }
}
