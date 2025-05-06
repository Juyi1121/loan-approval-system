package com.example.loan_approval_system.loan_core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.loan_approval_system.loan_core.entity.*;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {

     /* Reviewer – 待審 */
     List<LoanApplication> findByStatusOrderByApplicationDateDesc(LoanStatus status);

     /* Reviewer / Admin – 全部 */
     List<LoanApplication> findAllByOrderByApplicationDateDesc();
 
     /* Applicant – 自己的 */
     List<LoanApplication> findByApplicantOrderByApplicationDateDesc(String applicant);
}
