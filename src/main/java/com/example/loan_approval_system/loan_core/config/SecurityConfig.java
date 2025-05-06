package com.example.loan_approval_system.loan_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 設定：
 * ─ applicant / reviewer / admin 三種帳號 (密碼皆 123，僅供開發)
 * ─ URL 權限分配：
 *   • APPLICANT : /loan/apply, /loan/my
 *   • REVIEWER  : /loan/pending, /loan/all, /loan/{id}/approve|reject
 *   • ADMIN     : /loan/** (含 reviewer 路徑)
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /** Demo 帳號（正式環境請換成 DB / LDAP） */
    @Bean
    public UserDetailsService users() {
        UserDetails applicant = User.withUsername("applicant")
                .password(passwordEncoder().encode("123"))
                .roles("APPLICANT").build();
        UserDetails reviewer  = User.withUsername("reviewer")
                .password(passwordEncoder().encode("123"))
                .roles("REVIEWER").build();
        UserDetails admin     = User.withUsername("admin")
                .password(passwordEncoder().encode("123"))
                .roles("ADMIN").build();
        return new InMemoryUserDetailsManager(applicant, reviewer, admin);
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                /* 靜態資源與登入頁放行 */
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()

                /* Applicant 專用 */
                .requestMatchers("/loan/apply", "/loan/my")
                    .hasRole("APPLICANT")

                /* Reviewer + Admin 共用 (⚠️ 先寫 hasAnyRole，再寫 Admin，順序很重要) */
                .requestMatchers("/loan/pending", "/loan/all",
                                 "/loan/*/approve", "/loan/*/reject")
                    .hasAnyRole("REVIEWER", "ADMIN")

                /* 其餘 /loan/** 僅 Admin 可見 */
                .requestMatchers("/loan/**").hasRole("ADMIN")

                /* 其他請求必須登入 */
                .anyRequest().authenticated()
        )

        /* 表單登入 */
        .formLogin(form -> form
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/post-login", true)   // 成功登入導向
        )

        /* 登出 */
        .logout(lg -> lg.logoutSuccessUrl("/login?logout"))

        /* 先停用 CSRF 以方便 Postman / HTML 表單測試 */
        .csrf(csrf -> csrf.disable())

        /* 也支援 HTTP Basic（測試用） */
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
