package com.example.loan_approval_system.loan_core.controller;

import com.example.loan_approval_system.loan_core.dto.LoanApplicationDTO;
import com.example.loan_approval_system.loan_core.entity.LoanApplication;
import com.example.loan_approval_system.loan_core.service.LoanApplicationService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;

/*把申請表單獨立出來*/

@Controller
@RequestMapping("/loan")
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanApplicationService loanApplicationService;

    @GetMapping("/apply")
    public String showApplyForm(Model model, HttpServletRequest request) {

        model.addAttribute("loanApplicationDto", new LoanApplicationDTO());
        return "loan/apply";
    }

    @PostMapping("/apply")
    public String apply(@Validated @ModelAttribute("loanApplicationDto") LoanApplicationDTO dto,
            BindingResult result,
            Model model) {
        if (result.hasErrors()) {
            return "loan/apply";
        }
        LoanApplication saved = loanApplicationService.applyForLoan(dto.getCompanyId(),
                dto.getLoanAmount(),
                dto.getTerm(),
                dto.getApplicant());
        return "redirect:/loan/detail/" + saved.getId();
    }
}