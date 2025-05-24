// src/main/java/com/example/loan_approval_system/loan_core/service/impl/LoanApplicationServiceImpl.java
package com.example.loan_approval_system.loan_core.service.impl;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.entity.LoanRisk;
import com.example.loan_approval_system.loan_core.entity.LoanStatus;
import com.example.loan_approval_system.loan_core.repository.LoanApplicationRepository;
import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import com.example.loan_approval_system.loan_core.repository.LoanRiskRepository;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;
import com.example.loan_approval_system.loan_core.service.RiskAssessmentService;
import com.example.loan_approval_system.loan_core.service.BlockchainService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class LoanApplicationServiceImpl implements LoanApplicationService {

    private final LoanApplicationRepository repo;
    private final CompanyRepository companyRepo;
    private final LoanRiskRepository riskRepo;
    private final RiskAssessmentService riskService;
    private final BlockchainService bcService;

    public LoanApplicationServiceImpl(LoanApplicationRepository repo,
                                      CompanyRepository companyRepo,
                                      LoanRiskRepository riskRepo,
                                      RiskAssessmentService riskService,
                                      BlockchainService bcService) {
        this.repo         = repo;
        this.companyRepo  = companyRepo;
        this.riskRepo     = riskRepo;
        this.riskService  = riskService;
        this.bcService    = bcService;
    }

    @Override
    public List<LoanApplication> listAll() {
        return repo.findAll();
    }

    @Override
    public List<LoanApplication> listByCurrentUser() {
        // 由 Spring Security 拿到當前登入者的 username
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return repo.findByApplicant(username);
    }

    @Override
    public List<LoanApplication> listPending() {
        return repo.findAll().stream()
                   .filter(l -> l.getStatus() == LoanStatus.PENDING)
                   .collect(Collectors.toList());
    }

    @Override
    public LoanApplication get(Long id) {
        return repo.findById(id).orElseThrow();
    }

    @Override
    public LoanApplication applyForLoan(Long companyId,
                                       Double amount,
                                       Integer term,
                                       String applicant) {
        var co = companyRepo.findById(companyId).orElseThrow();
        LoanApplication app = new LoanApplication();
        app.setCompany(co);
        app.setLoanAmount(amount);
        app.setTerm(term);
        app.setApplicant(applicant);
        app.setStatus(LoanStatus.PENDING);
        app.setApplicationDate(LocalDateTime.now());

        LoanApplication savedApp = repo.save(app);

        Double score = riskService.assess(savedApp);
        String classification = score > 600 ? "Low Risk" : "High Risk";

        LoanRisk risk = new LoanRisk(
            savedApp,
            score,
            score,
            classification,
            classification
        );
        riskRepo.save(risk);

        savedApp.setRiskScore(score);
        savedApp.setRiskStatus(classification);
        savedApp.getRisks().add(risk);
        repo.save(savedApp);

        bcService.recordLoanApplication(savedApp);

        return savedApp;
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }

    @Override
    public void approve(Long id) {
        LoanApplication app = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("找不到申請：" + id));
        app.setStatus(LoanStatus.APPROVED);
        app.setDecisionDate(LocalDateTime.now());
        repo.save(app);
    }

    @Override
    public void reject(Long id) {
        LoanApplication app = repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("找不到申請：" + id));
        app.setStatus(LoanStatus.REJECTED);
        app.setDecisionDate(LocalDateTime.now());
        repo.save(app);
    }
}
