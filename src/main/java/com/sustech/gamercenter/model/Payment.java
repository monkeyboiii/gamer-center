package com.sustech.gamercenter.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "users_purchases")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "balance_change")
    Double balanceChange;

    @Column(name = "created_at")
    Timestamp createdAt;

    @Column(name = "rest_balance")
    Double restBalance;


    public Payment() {
    }

    public Payment(Long userId, Double balanceChange, Double restBalance) {
        this.userId = userId;
        this.balanceChange = balanceChange;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.restBalance = restBalance;
    }

    public Payment(Long id, Long userId, Double balanceChange, Double restBalance) {
        this(userId, balanceChange, restBalance);
        this.id = id;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(Double balanceChange) {
        this.balanceChange = balanceChange;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Double getRestBalance() {
        return restBalance;
    }

    public void setRestBalance(Double restBalance) {
        this.restBalance = restBalance;
    }
}
