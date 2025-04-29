package com.example.loan_approval_system.loan_core.controller;

import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
@Controller @RequestMapping("/company") @RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    @GetMapping("/add")
    public String addForm(Model m) { m.addAttribute("company", new Company()); return "addCompany"; }

    @PostMapping("/save")
    public String save(@ModelAttribute Company company) {
        service.save(company);
        return "companySuccess";
    }
}