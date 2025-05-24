package com.example.loan_approval_system.loan_core.service.impl;

import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.service.BlockchainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 示範實作：僅記錄 log；未來可替換為真正的區塊鏈 SDK 呼叫。
 */
@Service
@Slf4j
public class BlockchainServiceImpl implements BlockchainService {

    @Override
    public void recordLoanApplication(LoanApplication application) {
    
        log.info("[Blockchain] LoanApplication(id={}) 已上鏈存證", application.getId());
    }
}