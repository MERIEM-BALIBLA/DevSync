package org.example.service;

import org.example.model.Token;
import org.example.model.User;
import org.example.repository.implementation.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public UserService() {
        this.userRepository = new UserRepository();
        this.tokenService = new TokenService();
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
        if (!user.isManager()) {
            Token token = new Token();
            token.setUser(user);
            token.setDailyTokens(2);
            token.setMonthlyTokens(1);
            token.setLastResetDate(LocalDate.now());
            tokenService.save(token);
        }
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .filter(e -> !e.isManager())
                .collect(Collectors.toList());
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
}
