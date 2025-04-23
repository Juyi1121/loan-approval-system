package com.example.loan_approval_system.loan_core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
  @GetMapping("/login")
    public String loginPage() {
        return "login"; // 這會去找 "login.html    }
}
}