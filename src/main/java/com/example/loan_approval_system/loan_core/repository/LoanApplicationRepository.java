package com.example.loan_approval_system.loan_core.repository;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.entity.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long> {
    List<LoanApplication> findByStatus(LoanStatus status);
    List<LoanApplication> findByApplicant(String applicant);
}