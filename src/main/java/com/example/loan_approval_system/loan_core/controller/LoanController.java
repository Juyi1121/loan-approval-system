package com.example.loan_approval_system.loan_core.controller;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.loan_approval_system.loan_core.entity.LoanStatus;
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

    /* ---------- 申請表單 ---------- */
    @GetMapping("/apply")
    public String showApplyForm(Model m) {
        m.addAttribute("companies", companyRepo.findAll());
        return "applyloan";
    }

    @PostMapping("/apply")
    public String processApply(@RequestParam Long companyId,
                               @RequestParam BigDecimal amount,
                               @RequestParam Integer term,
                               Principal principal) {

        loanService.applyForLoan(companyId,
                                 amount.doubleValue(),
                                 term,
                                 principal.getName());
        return "redirect:/loan/my?success";
    }

    /* ---------- Reviewer ---------- */
    @GetMapping("/pending")
    public String pending(Model m) {
        m.addAttribute("loans", loanService.findByStatus(LoanStatus.PENDING));
        return "reviewLoan";
    }

    @GetMapping("/all")
    public String all(Model m) {
        m.addAttribute("loans", loanService.findAll());
        m.addAttribute("pageTitle", "所有審核紀錄");   //
        return "reviewLoan";
    }

    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        loanService.approve(id);
        return "redirect:/loan/pending";
    }

    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        loanService.reject(id);
        return "redirect:/loan/pending";
    }

    /* ---------- Applicant ---------- */
    @GetMapping("/my")
    public String myLoans(Model m, Principal principal) {
        m.addAttribute("loans", loanService.findByApplicant(principal.getName()));
        return "myLoan";
    }
}
