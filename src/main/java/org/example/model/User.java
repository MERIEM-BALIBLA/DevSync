package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
    @Column(name = "is_manager")
    private boolean isManager = false;

    @OneToMany(mappedBy = "assignedUser", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> userTaks;

    @OneToMany(mappedBy = "createdBy", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Task> managerTask;

    @OneToOne(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Token token;

    public User() {
    }

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
