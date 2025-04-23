package com.example.loan_approval_system.loan_core.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoanResponseDTO {
    private Long applicationId;
    private String riskStatus;
    private LocalDateTime applicationDate;
}
