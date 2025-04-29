package com.example.loan_approval_system.loan_core.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.loan_approval_system.loan_core.service.LoanApplicationService;

@Controller
@RequestMapping("/loan")
public class LoanController {

    private final LoanApplicationService service;

    public LoanController(LoanApplicationService service) {
        this.service = service;
    }

    /* ================ Reviewer 操作 ================ */

    /** 待審核清單 */
    @PreAuthorize("hasRole('REVIEWER')")
    @GetMapping("/pending")
    public String pending(Model m) {
        m.addAttribute("loans", service.findPending());
        return "reviewLoan";       // templates/reviewLoan.html
    }

    /** 核准 */
    @PreAuthorize("hasRole('REVIEWER')")
    @PostMapping("/{id}/approve")
    public String approve(@PathVariable Long id) {
        service.approve(id);
        return "redirect:/loan/pending";
    }

    /** 拒絕 */
    @PreAuthorize("hasRole('REVIEWER')")
    @PostMapping("/{id}/reject")
    public String reject(@PathVariable Long id) {
        service.reject(id);
        return "redirect:/loan/pending";
    }
}
