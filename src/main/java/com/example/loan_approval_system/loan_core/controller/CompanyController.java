package com.example.loan_approval_system.loan_core.controller;

import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    /** 顯示新增公司頁面 */
    @GetMapping("/add")
    public String showAddCompanyForm() {
        return "addCompany";            // templates/addCompany.html
    }

    /** 處理公司資料保存 */
    @PostMapping("/save")
    public String saveCompany(@RequestParam String companyName,
                              @RequestParam double creditScore,
                              @RequestParam double debtRatio,
                              @RequestParam double revenue,
                              Model model) {

        Company saved = companyService.saveCompany(companyName, creditScore, debtRatio, revenue);
        model.addAttribute("company", saved);          // 傳遞整個物件
        return "companySuccess";       // templates/companySuccess.html
    }
}
