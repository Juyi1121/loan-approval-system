package com.example.loan_approval_system.loan_core.service;

import com.example.loan_approval_system.loan_core.entity.Company;
import java.util.List;

public interface CompanyService {
    Company save(Company company);
    Company get(Long id);
    void delete(Long id);
    List<Company> listAll();
}
