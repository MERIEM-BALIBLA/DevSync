package org.example.service;

import org.example.model.User;
import org.example.repository.implementation.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }

    public List<User> getAllUsers() {
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

    public void close() {
        userRepository.close();
    }
}
