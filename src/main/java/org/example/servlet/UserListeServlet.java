package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.io.IOException;
import java.util.List;

@WebServlet("/userList")
public class UserListeServlet extends HttpServlet {
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = new UserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        List<User> users = userRepository.getAllUsers();
        req.setAttribute("users", users);  // Set the users list as a request attribute
        req.getRequestDispatcher("/vue/ListUsers.jsp").forward(req, resp);
    }


    @Override
    public void destroy() {
        userRepository.close(); // Fermez le UserRepository si nécessaire
        super.destroy();
    }
}
