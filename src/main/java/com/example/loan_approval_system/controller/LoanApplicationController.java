package com.example.loan_approval_system.controller;

import com.example.loan_approval_system.dto.LoanRequestDTO;
import com.example.loan_approval_system.entity.LoanApplication;
import com.example.loan_approval_system.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/loan")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanService;

    /** 顯示貸款申請表單 */
    @GetMapping("/apply")
    public String showApplyLoanForm(Model model) {
        model.addAttribute("loanRequestDTO", new LoanRequestDTO());
        return "applyLoan";          // templates/applyLoan.html
    }

    /** 接收貸款申請（AJAX 或 Fetch） */
    @PostMapping("/apply")
    @ResponseBody
    public ResponseEntity<?> apply(@RequestBody LoanRequestDTO req) {

        LoanApplication app = loanService.applyForLoan(req.getCompanyId(), req.getLoanAmount());

        return ResponseEntity.ok(new LoanApplicationResponse(
                app.getId(),
                app.getRiskStatus(),
                app.getApplicationDate()
        ));
    }

    /** 顯示貸款結果頁（範例用 GET） */
    @GetMapping("/result")
    public String showLoanResult(Model model) {
        model.addAttribute("message", "Your loan application has been submitted successfully!");
        return "loanResult";         // templates/loanResult.html
    }

    /* ===== 內部回應 DTO ===== */
    public static class LoanApplicationResponse {
        private Long applicationId;
        private String riskStatus;
        private java.time.LocalDateTime applicationDate;

        public LoanApplicationResponse(Long id, String risk, java.time.LocalDateTime date) {
            this.applicationId = id;
            this.riskStatus    = risk;
            this.applicationDate = date;
        }
        public Long getApplicationId() { return applicationId; }
        public String getRiskStatus()  { return riskStatus; }
        public java.time.LocalDateTime getApplicationDate() { return applicationDate; }
    }
}
