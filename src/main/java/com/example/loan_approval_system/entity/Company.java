package com.example.loan_approval_system.entity;


    import jakarta.persistence.*;
    import lombok.*;
    
    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "company")
    public class Company {
    
        public Company(String companyName, double creditScore, double debtRatio, double revenue) {
            this.companyName = companyName;
            this.creditScore = creditScore;
            this.debtRatio = debtRatio;
            this.revenue = revenue;
        }
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
    
        @Column(nullable = false, unique = true)
        private String companyName;
    
        private double revenue; // 年收入
        private double debtRatio; // 負債比率
        private double creditScore; // 信用評分
}

