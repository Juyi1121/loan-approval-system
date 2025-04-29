package com.example.loan_approval_system.loan_core.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.loan_approval_system.loan_core.entity.*;

public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {

    List<LoanApplication> findByStatus(LoanStatus status);
}
