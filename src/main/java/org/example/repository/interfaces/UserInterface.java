package org.example.repository.interfaces;

import org.example.model.User;

import java.util.List;

public interface UserInterface {
    void insertUser(User user);

    List<User> getAllUsers();

    void deleteUser(int userId);

    void updateUser(User user);

    User findById(int userId);
}
