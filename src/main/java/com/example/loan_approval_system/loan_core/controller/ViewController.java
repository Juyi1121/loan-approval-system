// src/main/java/com/example/loan_approval_system/loan_core/controller/ViewController.java
package com.example.loan_approval_system.loan_core.controller;

import com.example.loan_approval_system.loan_core.service.CompanyService;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ViewController {
    private final CompanyService companyService;
    private final LoanApplicationService loanService;

    public ViewController(CompanyService companyService, LoanApplicationService loanService) {
        this.companyService = companyService;
        this.loanService = loanService;
    }

    /** 我的申請列表 */
    @GetMapping("/loan/my-applicant")
    public String myApplicant(Model m) {
        m.addAttribute("loans", loanService.listByCurrentUser());
        return "loan/my-applicant";
    }

    /** 待審核列表 (Reviewer) */
    @GetMapping("/loan/pending")
    public String pendingLoans(Model m) {
        m.addAttribute("loans", loanService.listPending());
        return "loan/pending";
    }

    /** 核准 */
    @PostMapping("/loan/pending/approve/{id}")
    public String approveLoan(@PathVariable Long id) {
        loanService.approve(id);
        return "redirect:/loan/pending";
    }

    /** 拒絕 */
    @PostMapping("/loan/pending/reject/{id}")
    public String rejectLoan(@PathVariable Long id) {
        loanService.reject(id);
        return "redirect:/loan/pending";
    }

    /** 所有申請 (Admin) */
    @GetMapping("/loan/all-admin")
    public String allAdminLoans(Model m) {
        m.addAttribute("loans", loanService.listAll());
        return "loan/all-admin";
    }

    /** 詳情 */
    @GetMapping("/loan/detail/{id}")
    public String detail(@PathVariable Long id, Model m) {
        m.addAttribute("loan", loanService.get(id));
        return "loan/detail";
    }

    /** 管理員刪除 */
    @GetMapping("/loan/all-admin/delete/{id}")
    public String deleteAdminLoan(@PathVariable Long id) {
        loanService.delete(id);
        return "redirect:/loan/all-admin";
    }
}
