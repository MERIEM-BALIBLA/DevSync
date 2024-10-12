package org.example.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    update
    @Column(name = "daily_tokens")
    private int dailyTokens;

//    delete
    @Column(name = "monthly_tokens")
    private int monthlyTokens;

    public Token() {
        this.dailyTokens = 2;
        this.monthlyTokens = 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public int getDailyTokens() {
        return dailyTokens;
    }

    public void setDailyTokens(int dailyTokens) {
        this.dailyTokens = dailyTokens;
    }

    public int getMonthlyTokens() {
        return monthlyTokens;
    }

    public void setMonthlyTokens(int monthlyTokens) {
        this.monthlyTokens = monthlyTokens;
    }
}
