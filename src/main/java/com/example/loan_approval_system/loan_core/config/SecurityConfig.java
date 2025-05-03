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

@Configuration
@EnableMethodSecurity   // 讓 @PreAuthorize 生效
public class SecurityConfig {

    /** Demo：記憶體帳號 —— 密碼都為 123 */
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
    public BCryptPasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/login", "/css/**", "/js/**",
                                 "/swagger-ui.html", "/v3/api-docs/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/reviewer/**").hasRole("REVIEWER")
                .requestMatchers("/applicant/**").hasRole("APPLICANT")
                .anyRequest().authenticated()
        )
        .formLogin(form -> form
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/post-login", true)   // 依身分導到各自首頁
        )
        .logout(lg -> lg.logoutSuccessUrl("/login?logout"))
        .csrf(csrf -> csrf.disable())
        .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
