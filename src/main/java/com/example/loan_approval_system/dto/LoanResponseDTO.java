package com.example.loan_approval_system.dto;
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
