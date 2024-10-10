package org.example.service;

import org.example.model.User;
import org.example.repository.implementation.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public void insertUser(User user) {
        userRepository.insertUser(user);
    }
    public List<User> getAllUsers() {
        return userRepository.getAllUsers()
                .stream()
                .filter(e -> !e.isManager()) // Filtre les utilisateurs non-managers
                .collect(Collectors.toList()); // Collecte le résultat dans une liste
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

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public void close() {
        userRepository.close();
    }
}
