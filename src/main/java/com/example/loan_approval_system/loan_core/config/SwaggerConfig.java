package com.example.loan_approval_system.loan_core.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.*;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title("Loan-Approval API")
                .description("申請 / 審核 / 管理 API")
                .version("v1.0"));
    }

    @Bean
    public GroupedOpenApi loanApi() {
        return GroupedOpenApi.builder()
                .group("loan")
                .pathsToMatch("/loan/**")
                .build();
    }
}
