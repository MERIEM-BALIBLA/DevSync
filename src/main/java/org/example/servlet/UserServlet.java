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
    private UserRepository userRepository;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        userRepository = new UserRepository();
        userService = new UserService();
    }

    @Override
/*
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false); // Ne pas créer une nouvelle session
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");

        if (user.getRole() != Role.ADMIN) {

            String action = req.getParameter("action");
            if ("add".equals(action)) {
                req.getRequestDispatcher("/vue/InsertUser.jsp").forward(req, resp);
            } else if ("edit".equals(action)) {
                String userId = req.getParameter("userId");
                User editUser = userService.findById(Integer.parseInt(userId));
                if (editUser != null) {
                    req.setAttribute("user", editUser);
                    req.getRequestDispatcher("/vue/UpdateUser.jsp").forward(req, resp);
                }
            } else {
                List<User> users = userService.getAllUsers();
                req.setAttribute("users", users);
                req.getRequestDispatcher("/vue/ListUsers.jsp").forward(req, resp);
            }
        }
    }
*/

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
        } else {
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
            Role role = Role.valueOf(req.getParameter("role"));
            String password = req.getParameter("password");
            String confirmPassword = req.getParameter("confirm-password");

            // Vérifiez que les mots de passe correspondent
            if (!password.equals(confirmPassword)) {
                req.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
                req.getRequestDispatcher("/vue/InsertUser.jsp").forward(req, resp);
                return;
            }

            // Hacher le mot de passe
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            User user = new User();
            user.setUsername(username);
            user.setEmail(email);
            user.setRole(role);
            user.setPassword(hashedPassword); // Stocker le mot de passe haché

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
            Role role = Role.valueOf(req.getParameter("role"));

            User user = new User();
            user.setId(Integer.parseInt(userId));
            user.setUsername(username);
            user.setEmail(email);
            user.setRole(role);

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
