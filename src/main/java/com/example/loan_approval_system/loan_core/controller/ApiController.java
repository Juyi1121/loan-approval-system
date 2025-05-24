package com.example.loan_approval_system.loan_core.controller;

import com.example.loan_approval_system.loan_core.dto.LoanRequestDTO;
import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 提供給前端 JavaScript 使用的 JSON API。
 * 目前僅包含申請貸款功能，可依需求擴充查詢、核准等端點。
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    private final LoanApplicationService loanService;

    public ApiController(LoanApplicationService loanService) {
        this.loanService = loanService;
    }

    /** 申請貸款（JSON） */
    @PostMapping("/applications")
    public ResponseEntity<Map<String, Object>> apply(@RequestBody LoanRequestDTO dto) {
        LoanApplication app = loanService.applyForLoan(
                dto.getCompanyId(),
                dto.getLoanAmount(),
                dto.getTerm(),
                dto.getApplicant());

        return ResponseEntity.ok(Map.of("id", app.getId()));
    }

    /** 查詢單筆申請（JSON） */
    @GetMapping("/applications/{id}")
    public ResponseEntity<LoanApplication> get(@PathVariable Long id) {
        LoanApplication app = loanService.get(id);
        return ResponseEntity.ok(app);
    }
}