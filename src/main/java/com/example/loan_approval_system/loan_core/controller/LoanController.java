package com.example.loan_approval_system.loan_core.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;

@Controller
@RequestMapping("/loan")
public class LoanController {

    private final LoanApplicationService loanService;
     private final CompanyRepository companyRepo;   // ← 新增

    public LoanController(LoanApplicationService loanService, CompanyRepository companyRepo) {
        this.loanService = loanService;
        this.companyRepo = companyRepo;
    }

    /* ---------- 申請人 (Applicant) ---------- */

    /** 顯示申請表單 (GET /loan/apply) */
    @GetMapping("/apply")
    public String showApplyForm(Model model) {
        model.addAttribute("companies", companyRepo.findAll());  // ★ 關鍵一行
        return "applyLoan";
    }


    /** 處理送出 (POST /loan/apply) */
    @PostMapping("/apply")
    public String processApply(@RequestParam Long companyId,
                               @RequestParam BigDecimal amount,
                               @RequestParam Integer term) {

        // 最陽春的 Service 呼叫：你可以先做一個 applySimple()
        loanService.applySimple(companyId, amount, term);

        // PRG (Post/Redirect/Get) 避免重複提交
        return "redirect:/loan/apply?success";
    }

    /* ---------- 審核員 (Reviewer) ---------- */

    /** 待審核清單 (GET /loan/pending) */
    @GetMapping("/pending")
    public String pending(Model model) {
        model.addAttribute("loans", loanService.findPending());
        return "reviewLoan";               // templates/reviewLoan.html
    }

    /** 核准 (POST /loan/{id}/approve) */
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        loanService.approve(id);
        return "redirect:/loan/pending";
    }

    /** 拒絕 (POST /loan/{id}/reject) */
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        loanService.reject(id);
        return "redirect:/loan/pending";
    }
}
