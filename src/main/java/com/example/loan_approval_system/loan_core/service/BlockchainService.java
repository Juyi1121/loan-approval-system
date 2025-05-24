package com.example.loan_approval_system.loan_core.service;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;

/**
 * 區塊鏈存證服務接口。
 */
public interface BlockchainService {

    /**
     * 將貸款申請資訊上鏈存證。
     * @param application 已持久化之 LoanApplication
     */
    void recordLoanApplication(LoanApplication application);
}