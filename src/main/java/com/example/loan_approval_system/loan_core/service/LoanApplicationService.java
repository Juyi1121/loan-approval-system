// src/main/java/com/example/loan_approval_system/loan_core/service/LoanApplicationService.java
package com.example.loan_approval_system.loan_core.service;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;

import java.util.List;

public interface LoanApplicationService {
    List<LoanApplication> listAll();
    List<LoanApplication> listByCurrentUser();
    List<LoanApplication> listPending();
    LoanApplication get(Long id);
    LoanApplication applyForLoan(Long companyId, Double amount, Integer term, String applicant);
    void delete(Long id);
    void approve(Long id);
    void reject(Long id);
}
