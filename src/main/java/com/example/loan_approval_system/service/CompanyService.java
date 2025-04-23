package com.example.loan_approval_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.loan_approval_system.entity.Company;
import com.example.loan_approval_system.repository.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;  // 假設使用 JPA Repository 來處理資料庫操作

    public Company saveCompany(String companyName, double creditScore, double debtRatio, double revenue) {
        Company company = new Company(companyName, creditScore, debtRatio, revenue);
        return companyRepository.save(company);  // 保存公司並返回保存後的對象
    }
}

