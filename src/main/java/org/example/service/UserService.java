package org.example.service;

import org.example.model.Token;
import org.example.model.User;
import org.example.repository.implementation.UserRepository;
import org.example.util.ExeptionHandler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserService(UserRepository userRepository, TokenService tokenService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    public User insertUser(User user) {
        if (user == null || user.equals(new User())) {
            throw new ExeptionHandler("user null");
        }
        String normalizedEmail = user.getEmail().toLowerCase();
        user.setEmail(normalizedEmail);
        userRepository.insertUser(user);
        if (!user.isManager()) {
            Token token = new Token();
            token.setUser(user);
            token.setDailyTokens(2);
            token.setMonthlyTokens(1);
            token.setLastResetDate(LocalDate.now());
            tokenService.save(token);
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .filter(e -> !e.isManager())
                .collect(Collectors.toList());
    }

    public List<User> getAll() {
        return userRepository.getAllUsers();
    }

    public void deleteUser(int userId) {
        userRepository.deleteUser(userId);
    }

    public void updateUser(User user) {
        userRepository.updateUser(user);
    }

    public User findById(int userId) {
        return userRepository.findById(userId);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void close() {
        userRepository.close();
    }

    public int count() {
        return this.getAllUsers().size();
    }
}

