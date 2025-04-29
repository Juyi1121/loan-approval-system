package com.example.loan_approval_system.loan_core.service;

import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * 創建新公司
     * @param company 資料傳輸物件
     * @return 新儲存的公司實體
     */
    public Company save(Company company) {
        return companyRepository.save(company);
    }

    /**
     * 根據公司名稱查找公司
     * @param companyName 公司名稱
     * @return Optional<Company>
     */
    public Optional<Company> findByCompanyName(String companyName) {
        return companyRepository.findByCompanyName(companyName);
    }

    /**
     * 查找所有公司
     * @return 所有公司
     */
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }
}
