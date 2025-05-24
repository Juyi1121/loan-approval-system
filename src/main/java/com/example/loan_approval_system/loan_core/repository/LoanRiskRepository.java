package com.example.loan_approval_system.loan_core.repository;

import com.example.loan_approval_system.loan_core.entity.LoanRisk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRiskRepository extends JpaRepository<LoanRisk, Long> {}