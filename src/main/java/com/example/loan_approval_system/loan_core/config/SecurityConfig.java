package com.example.loan_approval_system.loan_core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity        // 讓 @PreAuthorize 等註解能運作
public class SecurityConfig {

    /* ==== Demo 用：三個 In-Memory 使用者 ==== */
    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder encoder) {

        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        UserDetails reviewer = User.withUsername("reviewer")
                .password(encoder.encode("reviewer123"))
                .roles("REVIEWER")
                .build();

        UserDetails applicant = User.withUsername("applicant")
                .password(encoder.encode("applicant123"))
                .roles("APPLICANT")
                .build();

        return new InMemoryUserDetailsManager(admin, reviewer, applicant);
    }

    /* ========= 核心：Filter Chain ========= */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            /* 1. 先把 CSRF 關掉（本機 demo）*/
            .csrf(csrf -> csrf.disable())

            /* 2. 路徑授權規則 */
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/", "/index", "/login",
                                     "/css/**", "/js/**", "/img/**",
                                     "/webjars/**", "/h2-console/**").permitAll()
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers("/reviewer/**").hasRole("REVIEWER")
                    .requestMatchers("/applicant/**").hasRole("APPLICANT")
                    .anyRequest().authenticated()
            )

            /* 3. 表單登入 */
            .formLogin(login -> login
                    .loginPage("/login")                // 自訂登入頁 (GET)
                    .loginProcessingUrl("/login")        // 表單 action (POST)
                    .defaultSuccessUrl("/post-login", true)
                    .permitAll()
            )

            /* 4. 登出 */
            .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()
            )

            /* 5. H2 console 需要關 frameOptions */
            .headers(h -> h.frameOptions(f -> f.disable()));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
