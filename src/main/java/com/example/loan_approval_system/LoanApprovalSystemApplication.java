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

        // 1. 若資料庫已經有這家公司就跳過
        if (companyRepo.findByCompanyName("測試公司A").isPresent()) {
            System.out.println("公司『測試公司A』已存在，略過新增！");
            return;
        }

        // 2. 建立測試公司
        Company c = new Company();
        c.setCompanyName("測試公司A");
        c.setRevenue(1_000_000);   // 年營收
        c.setDebtRatio(0.7);       // 負債比
        c.setCreditScore(580);     // 信用分數
        Company saved = companyRepo.save(c);

        // 3. 送出貸款申請（公司 ID, 金額, 期數, 申請人帳號）
        String applicant = "demo@applicant.com";
        LoanApplication result =
            loanService.applyForLoan(saved.getId(), 500_000, 12, applicant);

        System.out.println("自動送出測試申請，狀態：" + result.getStatus());
    }
}
