// src/main/java/com/example/loan_approval_system/loan_core/service/impl/CompanyServiceImpl.java
package com.example.loan_approval_system.loan_core.service.impl;

import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import com.example.loan_approval_system.loan_core.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepo;
    public CompanyServiceImpl(CompanyRepository companyRepo) { this.companyRepo = companyRepo; }

    @Override public Company save(Company company) { return companyRepo.save(company); }
    @Override public Company get(Long id) { return companyRepo.findById(id).orElseThrow(); }
    @Override public void delete(Long id) { companyRepo.deleteById(id); }
    @Override public List<Company> listAll() { return companyRepo.findAll(); }
}
