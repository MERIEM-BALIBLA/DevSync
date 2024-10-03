package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.repository.implementation.UserRepository;
import org.example.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/userList")
public class UserServlet extends HttpServlet {
    private UserRepository userRepository;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userRepository = new UserRepository();
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            req.getRequestDispatcher("/vue/InsertUser.jsp").forward(req, resp);
        } else if ("edit".equals(action)) {
            String userId = req.getParameter("userId");
            User user = userService.findById(Integer.parseInt(userId));
            if (user != null) {
                req.setAttribute("user", user);
                req.getRequestDispatcher("/vue/UpdateUser.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/userList");
            }
        }
        else {
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/vue/ListUsers.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);

            try {
                userService.insertUser(user);
                resp.sendRedirect("userList");
            } catch (Exception e) {
                e.printStackTrace(); // Log l'erreur pour le débogage
            }
        } else if ("delete".equals(action)) {
            String userId = req.getParameter("userId");

            if (userId != null) {
                try {
                    userService.deleteUser(Integer.parseInt(userId)); // Assurez-vous que deleteUser accepte un ID et supprime l'utilisateur
                    resp.sendRedirect(req.getContextPath() + "/userList");
                } catch (Exception e) {
                    e.printStackTrace();
                    // Gérer l'erreur, éventuellement rediriger vers une page d'erreur
                }
            }
        } else if ("edit".equals(action)) {
            String userId = req.getParameter("userId");
            String username = req.getParameter("username");
            String email = req.getParameter("email");

            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setUsername(username);
            user.setEmail(email);

            try {
                userService.updateUser(user); // Appelez la méthode d'update
                resp.sendRedirect(req.getContextPath() + "/userList"); // Redirige vers la liste des utilisateurs après mise à jour
            } catch (Exception e) {
                e.printStackTrace();
                // Gérer l'erreur, éventuellement rediriger vers une page d'erreur
            }
        }
    }


    @Override
    public void destroy() {
        userService.close(); // Fermez le UserRepository si nécessaire
        super.destroy();
    }
}
