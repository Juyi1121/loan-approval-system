package com.example.loan_approval_system.loan_core.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService users(PasswordEncoder encoder) {
        UserDetails applicant = User.builder()
                .username("applicant")
                .password(encoder.encode("123"))
                .roles("APPLICANT")
                .build();

        UserDetails reviewer = User.builder()
                .username("reviewer")
                .password(encoder.encode("123"))
                .roles("REVIEWER")
                .build();

        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(applicant, reviewer, admin);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                // 關閉 CSRF（示例階段）
                .csrf(csrf -> csrf.disable())
                // 授權設定
                .authorizeHttpRequests(auth -> auth
                        // 靜態資源 & 公开页面
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/", "/index", "/login", "/post-login", "/error").permitAll()

                        // 單筆詳情：所有登入用戶都可以查看
                        .requestMatchers(HttpMethod.GET, "/loan/detail/**")
                        .hasAnyRole("APPLICANT", "REVIEWER", "ADMIN")

                        // APPLICANT
                        .requestMatchers(HttpMethod.GET, "/loan/apply", "/loan/my-applicant")
                        .hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.POST, "/loan/apply")
                        .hasRole("APPLICANT")
                        // 用JSON 申請 API
                        .requestMatchers(HttpMethod.POST, "/api/applications")
                        .hasRole("APPLICANT")
                        .requestMatchers(HttpMethod.GET, "/api/applications/**")
                        .hasAnyRole("APPLICANT", "REVIEWER", "ADMIN")

                        // REVIEWER
                        .requestMatchers(HttpMethod.GET, "/loan/pending")
                        .hasRole("REVIEWER")
                        .requestMatchers(HttpMethod.POST,
                                "/loan/pending/approve/**",
                                "/loan/pending/reject/**")
                        .hasAnyRole("REVIEWER", "ADMIN")

                        // ADMIN
                        .requestMatchers(HttpMethod.GET, "/loan/all-admin")
                        .hasAnyRole("ADMIN", "REVIEWER")
                        .requestMatchers(HttpMethod.POST, "/loan/all-admin/delete/**")
                        .hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/company/add", "/company/all")
                        .hasAnyRole("ADMIN", "APPLICANT", "REVIEWER")
                        .requestMatchers(HttpMethod.POST, "/company/save")
                        .hasAnyRole("ADMIN", "APPLICANT", "REVIEWER")

                        // 其它所有請求都要登入
                        .anyRequest().authenticated())

                // 登入設定
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/post-login", true)
                        .failureUrl("/login?error")
                        .permitAll())
                // 登出設定
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());

        return http.build();
    }
}
