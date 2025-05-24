// src/main/java/com/example/loan_approval_system/LoanApprovalSystemApplication.java
package com.example.loan_approval_system;

import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoanApprovalSystemApplication implements CommandLineRunner {

    @Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private LoanApplicationService loanService;

    public static void main(String[] args) {
        SpringApplication.run(LoanApprovalSystemApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // 如果已經有「測試公司A」，就跳過
        if (companyRepo.findByCompanyName("測試公司A").isPresent()) {
            System.out.println("公司『測試公司A』已存在，略過新增！");
            return;
        }
        // 建立測試公司
        Company c = new Company();
        c.setCompanyName("測試公司A");
        c.setRevenue(1_000_000);
        c.setDebtRatio(0.7);
        c.setCreditScore(580);
        Company saved = companyRepo.save(c);

        // 自動送出測試申請
        LoanApplication result = loanService.applyForLoan(
            saved.getId(), 500_000.0, 12, "demo@applicant.com"
        );
        System.out.println("自動送出測試申請，狀態：" + result.getStatus());
    }
}
