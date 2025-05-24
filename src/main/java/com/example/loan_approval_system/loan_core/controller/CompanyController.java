package com.example.loan_approval_system.loan_core.controller;

import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公司管理 Controller
 */
@Controller
@RequestMapping("/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService service;

    /** 顯示「新增公司」表單 */
    @GetMapping("/add")
    public String addForm(Model m) {
        m.addAttribute("company", new Company());
        return "addCompany";
    }

    /** 處理「新增公司」提交 */
    @PostMapping("/save")
    public String save(@ModelAttribute Company company, Model m) {
        Company saved = service.save(company);
        m.addAttribute("company", saved);
        return "companySuccess";
    }

    /** 列出所有公司 */
    @GetMapping("/all")
    public String listAll(Model m) {
        List<Company> companies = service.listAll();
        m.addAttribute("companies", companies);
        return "company/list";
    }

    /** 顯示單一公司詳細 */
    @GetMapping("/{id}")
    public String detail(@PathVariable Long id, Model m) {
        Company company = service.get(id);
        m.addAttribute("company", company);
        return "company/detail";
    }

    /** 刪除公司並重導向列表 */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/company/all";
    }
}
