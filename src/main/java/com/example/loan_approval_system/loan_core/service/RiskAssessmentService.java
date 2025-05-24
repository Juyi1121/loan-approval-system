package com.example.loan_approval_system.loan_core.service;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;

public interface RiskAssessmentService {
    double assess(LoanApplication app);
}
