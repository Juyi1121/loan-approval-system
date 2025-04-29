package com.example.loan_approval_system.loan_core.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;

/**
 * 啟用 Thymeleaf + Spring Security 整合的方言 (Dialect)。
 * 有了它，模板裡就能使用 #authorization、sec:authorize … 等語法。
 */
@Configuration
public class ThymeleafConfig {

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }
}