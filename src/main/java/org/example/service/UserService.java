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
//        if (user == null || user.equals(new User())) {
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty() || user.getUsername() == null || user.getPassword() == null) {
            throw new ExeptionHandler("user null or missing required fields");
        }
        if (this.findByEmail(user.getEmail()) != null) {
            throw new ExeptionHandler("user already exist");
        }

        String normalizedEmail = user.getEmail().toLowerCase();
        user.setEmail(normalizedEmail);
        int userId = user.getId();

        String newEmail = user.getEmail();
        boolean emailExists = this.getAllUsers().stream()
                .filter(u -> u.getId() != userId)
                .anyMatch(u -> u.getEmail().equals(newEmail));

        if (emailExists) {
            throw new ExeptionHandler("Email already exists");
        }

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

    public boolean deleteUser(int userId) {
        if (userId <= 0) { // Check for invalid userId
            throw new ExeptionHandler("The id of user is null or invalid!!");
        }
        return userRepository.deleteUser(userId);
    }

    public User updateUser(User user) {
        if (user == null || user.getUsername().isEmpty() || user.getEmail().isEmpty() || user.getPassword().isEmpty()) {
            throw new ExeptionHandler("Please verify users Infromations ");
        }
        return userRepository.updateUser(user);
    }

    public User findById(int userId) {
        if (userId == 0) {
            throw new ExeptionHandler("Invalid id");
        }
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

