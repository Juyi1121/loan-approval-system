package com.example.loan_approval_system.loan_core.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 公司實體，用於儲存公司基本資料。
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 公司名稱，資料庫欄位 company_name */
    @Column(name = "company_name", nullable = false, unique = true)
    private String companyName;

    /** 年收入 */
    private double revenue;

    /** 負債比率 */
    private double debtRatio;

    /** 信用評分 */
    private double creditScore;

    /**
     * 補充建構子：只設定公司名稱、信用評分、負債比率、營收。
     */
    public Company(String companyName, double creditScore, double debtRatio, double revenue) {
        this.companyName = companyName;
        this.creditScore = creditScore;
        this.debtRatio = debtRatio;
        this.revenue = revenue;
    }

    /**
     * 為了讓 Thymeleaf 可以用 ${company.name} 取得公司名稱，
     * 新增一個 getter，並且不對應到資料庫的任何欄位。
     */
    @Transient
    public String getName() {
        return this.companyName;
    }
}
