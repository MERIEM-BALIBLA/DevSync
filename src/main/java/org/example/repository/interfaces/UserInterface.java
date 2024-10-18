/*
package org.example.repository.interfaces;

import org.example.model.User;

import java.util.List;

public interface UserInterface {
    User insertUser(User user);

    List<User> getAllUsers();

    void deleteUser(int userId);

    void updateUser(User user);

    User findById(int userId);
}
*/
package org.example.repository.interfaces;

import org.example.model.User;
import java.util.List;

public interface UserInterface {
    User insertUser(User user);
    List<User> getAllUsers();
    void deleteUser(int userId);
    void updateUser(User user);
    User findById(int userId);
    User findByEmail(String email);
    void close();
}