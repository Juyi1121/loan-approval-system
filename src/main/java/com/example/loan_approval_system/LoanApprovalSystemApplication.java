package com.example.loan_approval_system;

//test
import com.example.loan_approval_system.loan_core.entity.Company;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;
import com.example.loan_approval_system.loan_core.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
//
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//


@SpringBootApplication


public class LoanApprovalSystemApplication implements CommandLineRunner{
	@Autowired
    private CompanyRepository companyRepo;

    @Autowired
    private LoanApplicationService loanService;

    
	public static void main(String[] args) {
		SpringApplication.run(LoanApprovalSystemApplication.class, args);
	}
    
	@Override
    public void run(String... args) {
        if (companyRepo.findByCompanyName("測試公司A").isEmpty()) { // <-- 檢查有沒有存在
            Company c = new Company();
            c.setCompanyName("測試公司A");
            c.setRevenue(1000000);
            c.setDebtRatio(0.7); // 高負債
            c.setCreditScore(580); // 信用偏低
    
            Company saved = companyRepo.save(c);
    
            var result = loanService.applyForLoan(saved.getId(), 500000);
            System.out.println("申請成功，風險評估為：" + result.getStatus());
        } else {
            System.out.println("公司 測試公司A 已存在，略過新增！");
        }
}
}