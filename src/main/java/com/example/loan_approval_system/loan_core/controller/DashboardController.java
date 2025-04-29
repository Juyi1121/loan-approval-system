package com.example.loan_approval_system.loan_core.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/** 依角色導到對應的 dashboard */
@Controller
public class DashboardController {

    /** 登入完成後統一轉向 */
    @GetMapping("/post-login")
    public String postLogin(Authentication auth) {
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")))
            return "redirect:/admin";
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_REVIEWER")))
            return "redirect:/reviewer";
        return "redirect:/applicant";
    }

    @GetMapping({"/", "/index"})
    public String index() { return "index"; }

    @GetMapping("/login")
    public String loginPage() { return "login"; }

    /* -- 三個 Dashboard -- */
    @GetMapping("/admin")
    public String admin(Model m) { m.addAttribute("name", "系統管理員"); return "admin/dashboard"; }

    @GetMapping("/reviewer")
    public String reviewer(Model m) { m.addAttribute("name", "審核人員"); return "reviewer/dashboard"; }

    @GetMapping("/applicant")
    public String applicant(Model m) { m.addAttribute("name", "申請人員"); return "applicant/dashboard"; }
}