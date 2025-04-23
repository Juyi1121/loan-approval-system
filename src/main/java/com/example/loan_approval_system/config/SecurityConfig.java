package com.example.loan_approval_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            /* ===== 授權規則 ===== */
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/login",
                        "/",                 // 首頁
                        "/company/add",
                        "/company/save",
                        "/loan/apply",
                        "/loan/result",
                        "/css/**", "/js/**", "/images/**"
                ).permitAll()
                .anyRequest().authenticated()
            )

            /* ===== 登入流程 ===== */
            .formLogin(form -> form
                .loginPage("/login")          // 自訂登入頁
                .defaultSuccessUrl("/", false) // 登入後回到上一頁；若無上一頁則回首頁
                .permitAll()
            )

            /* ===== 登出 ===== */
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )

            /* ===== 其餘設定 ===== */
            .csrf(csrf -> csrf.disable())     // Demo 先關閉
            .sessionManagement(sess -> sess
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .expiredUrl("/login?expired")
            );

        return http.build();
    }
}
