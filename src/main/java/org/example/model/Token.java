package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    //    update
    @Column(name = "daily_tokens")
    private int dailyTokens;

    //    delete
    @Column(name = "monthly_tokens")
    private int monthlyTokens;

    @Column(name = "reset_date")
    private LocalDate lastResetDate;

    public Token() {

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

    public User setUser(User user) {
        return this.user = user;
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

    public LocalDate getLastResetDate() {
        return lastResetDate;
    }

    public void setLastResetDate(LocalDate lastResetDate) {
        this.lastResetDate = lastResetDate;
    }
}
