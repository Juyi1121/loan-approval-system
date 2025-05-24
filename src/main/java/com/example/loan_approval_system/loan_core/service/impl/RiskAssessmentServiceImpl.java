package com.example.loan_approval_system.loan_core.service.impl;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.service.RiskAssessmentService;
import org.springframework.stereotype.Service;

@Service
public class RiskAssessmentServiceImpl implements RiskAssessmentService {

    @Override
    public double assess(LoanApplication app) {
        // 風險分數算法示例，根據公司財務指標計算
        return (1.0 - app.getCompany().getDebtRatio()) * app.getCompany().getCreditScore();
    }
}
