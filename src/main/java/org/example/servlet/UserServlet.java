package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.model.enums.Role;
import org.example.repository.implementation.UserRepository;
import org.example.service.UserService;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.List;

@WebServlet("/userList")
public class UserServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user.isManager()) {
            String action = req.getParameter("action");
            if ("add".equals(action)) {
                req.getRequestDispatcher("/vue/admin/users/InsertUser.jsp").forward(req, resp);
            } else if ("edit".equals(action)) {
                String userId = req.getParameter("userId");
                User editUser = userService.findById(Integer.parseInt(userId));
                if (editUser != null) {
                    req.setAttribute("user", editUser);
                    req.getRequestDispatcher("/vue/admin/users/UpdateUser.jsp").forward(req, resp);
                } else {
                    resp.sendRedirect(req.getContextPath() + "/userList");
                }
            } else {
                List<User> users = userService.getAllUsers();
                req.setAttribute("users", users);
                req.getRequestDispatcher("/vue/admin/users/ListUsers.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
        }
    }

    @Override

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("add".equals(action)) {
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String role = req.getParameter("role");
            boolean isManager = "ADMIN".equals(role); // true si ADMIN, sinon false
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirm-password");

            if (!password.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
                req.getRequestDispatcher("/vue/admin/users/InsertUser.jsp").forward(req, resp);
                return;
            }

            // Hacher le mot de passe
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setManager(isManager);
            user.setPassword(hashedPassword);

            try {
                userService.insertUser(user);
                resp.sendRedirect("userList");
            } catch (Exception e) {
                e.printStackTrace();
                req.setAttribute("errorMessage", "Erreur lors de l'ajout de l'utilisateur.");
                req.getRequestDispatcher("/vue/admin/users/InsertUser.jsp").forward(req, resp);
            }
        } else if ("delete".equals(action)) {
            String userId = req.getParameter("userId");

            if (userId != null) {
                try {
                    userService.deleteUser(Integer.parseInt(userId)); // Assurez-vous que deleteUser accepte un ID et supprime l'utilisateur
                    resp.sendRedirect(req.getContextPath() + "/userList");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if ("edit".equals(action)) {
            String userId = req.getParameter("userId");
            String username = req.getParameter("username");
            String email = req.getParameter("email");
            String role = req.getParameter("role");
            boolean isManager = "ADMIN".equals(role);
//            String password = req.getParameter("password");

            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setUsername(username);
            user.setEmail(email);
            user.setManager(isManager);

            try {
                userService.updateUser(user);
                resp.sendRedirect(req.getContextPath() + "/userList");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void destroy() {
        userService.close();
        super.destroy();
    }
}
