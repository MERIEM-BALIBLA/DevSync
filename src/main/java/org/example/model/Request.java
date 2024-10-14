package org.example.model;

import jakarta.persistence.*;
import org.example.model.enums.RequetStatus;

import java.time.LocalDate;
@Entity
@Table (name = "requets")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Enumerated(EnumType.STRING)
    private RequetStatus status;

    private LocalDate createdAt;

    public Request() {
        this.createdAt = LocalDate.now(); // Date de création par défaut
        this.status = RequetStatus.PENDING; // Statut par défaut
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public RequetStatus getStatus() {
        return status;
    }

    public void setStatus(RequetStatus status) {
        this.status = status;
    }
}
