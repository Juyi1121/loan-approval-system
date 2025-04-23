package com.example.loan_approval_system.loan_core.service;
import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import com.example.loan_approval_system.loan_core.repository.LoanApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanRepo;

    @Autowired
    private CompanyRepository companyRepo;

    // 建立貸款申請並自動評估風險
    @Transactional
    public LoanApplication applyForLoan(Long companyId, double loanAmount) {
        Company company = companyRepo.findById(companyId)
                .orElseThrow(() -> new RuntimeException("公司不存在"));

        String riskLevel = evaluateRisk(company);

        LoanApplication application = new LoanApplication();
        application.setCompanyId(companyId);
        application.setLoanAmount(loanAmount);
        application.setApplicationDate(LocalDateTime.now());

        // 將風險結果當作狀態先儲存（後續可以拓展成多階段審核）
        application.setStatus(riskLevel); // High Risk / Medium Risk / Low Risk

        return loanRepo.save(application);
    }

    // 風險評估邏輯
    private String evaluateRisk(Company company) {
        double debtRatio = company.getDebtRatio();
        double creditScore = company.getCreditScore();

        if (debtRatio > 0.6 || creditScore < 600) {
            return "High Risk";
        } else if (debtRatio > 0.3 || creditScore <= 700) {
            return "Medium Risk";
        } else {
            return "Low Risk";
        }
    }
}
