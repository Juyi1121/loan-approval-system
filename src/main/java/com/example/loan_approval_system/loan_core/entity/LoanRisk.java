package com.example.loan_approval_system.loan_core.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

/**
 * 對應資料表 loan_risk
 */
@Entity
@Table(name = "loan_risk")
@DynamicInsert // 只 INSERT 非 null 欄位，讓 DB 預設值生效
public class LoanRisk {

    // 主鍵 ID，自動遞增
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 評估時間，資料庫欄位 assessed_at NOT NULL，預設 CURRENT_TIMESTAMP
    @Column(name = "assessed_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @CreationTimestamp // Hibernate 自動塞入建立時間
    private LocalDateTime assessedAt;

    // 額外的評估時間，可為 null
    @Column(name = "evaluated_at")
    private LocalDateTime evaluatedAt;

    // 風險分數，資料庫欄位 risk_score，預設 0.0
    @Column(name = "risk_score", nullable = true, columnDefinition = "DOUBLE DEFAULT 0.0")
    @ColumnDefault("0.0") // Hibernate 在 DDL 時也會加上 DEFAULT 0.0
    private Double riskScore;

    // 另一個分數欄位 score，NOT NULL，但因有預設值可不在 INSERT 中帶入
    @Column(name = "score", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    @ColumnDefault("0.0")
    private Double score;

    // 風險分類，可為 null，長度最多 50
    @Column(name = "classification", length = 50)
    private String classification;

    // 風險狀態，NOT NULL
    @Column(name = "risk_status", nullable = false)
    private String riskStatus;

    // 與貸款申請主表的多對一關聯（foreign key=loan_app_id）
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loan_app_id", nullable = false, foreignKey = @ForeignKey(name = "fk_loanrisk_loanapp"))
    private LoanApplication loanApplication;

    // 建構子
    public LoanRisk() {
    }// JPA 需要無參構造子

    public LoanRisk(LoanApplication loanApplication,
            Double riskScore,
            Double score,
            String classification,
            String riskStatus) {
        this.loanApplication = loanApplication;
        this.riskScore = riskScore;
        this.score = score;
        this.classification = classification;
        this.riskStatus = riskStatus;
        // assessedAt 由 @CreationTimestamp 自動設定
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getAssessedAt() {
        return assessedAt;
    }

    public void setAssessedAt(LocalDateTime assessedAt) {
        this.assessedAt = assessedAt;
    }

    public LocalDateTime getEvaluatedAt() {
        return evaluatedAt;
    }

    public void setEvaluatedAt(LocalDateTime evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public String getRiskStatus() {
        return riskStatus;
    }

    public void setRiskStatus(String riskStatus) {
        this.riskStatus = riskStatus;
    }

    public LoanApplication getLoanApplication() {
        return loanApplication;
    }

    public void setLoanApplication(LoanApplication loanApplication) {
        this.loanApplication = loanApplication;
    }
}
