package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.model.enums.Role;
import org.example.repository.implementation.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/auth")
public class AuthServlet extends HttpServlet {
    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        userRepository = new UserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("signUp".equals(action)) {
            req.getRequestDispatcher("/vue/auth/SignUp.jsp").forward(req, resp);
        } else if ("login".equals(action)) {
            req.getRequestDispatcher("/vue/auth/Login.jsp").forward(req, resp);
        } else if ("logout".equals(action)) {
            handleLogout(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("signUp".equals(action)) {
            handleRegistration(req, resp);
        } else if ("login".equals(action)) {
            handleLogin(req, resp);
        }
    }

    private void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm-password");
        Role role = Role.USER;

        // Vérifiez que les mots de passe correspondent
        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            req.getRequestDispatcher("/vue/auth/signUp.jsp").forward(req, resp);
            return;
        }

        // Hachage du mot de passe
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        // Création de l'utilisateur
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        // Insertion de l'utilisateur dans la base de données
        userRepository.insertUser(user); // Assurez-vous que cette méthode ne lance pas d'exception

        // Enregistrer l'utilisateur dans la session
        req.getSession().setAttribute("user", user); // Enregistrer l'utilisateur dans la session
        resp.sendRedirect(req.getContextPath() + "/dashboard"); // Redirige vers le tableau de bord
    }

/*
    private void handleRegistration(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirm-password");
        Role role = Role.USER;

        if (!password.equals(confirmPassword)) {
            req.setAttribute("errorMessage", "Les mots de passe ne correspondent pas.");
            req.getRequestDispatcher("/vue/auth/signUp.jsp").forward(req, resp);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setRole(role);

        userRepository.insertUser(user);
        resp.sendRedirect(req.getContextPath() + "/dashboard"); // Chemin vers le tableau de bord

    }
*/

    private void handleLogin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = userRepository.findByEmail(email);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // Authentification réussie
            req.getSession().setAttribute("user", user); // Enregistrer l'utilisateur dans la session

            // Vérifier le rôle de l'utilisateur
            if (user.isManager()) {
                resp.sendRedirect(req.getContextPath() + "/userList");
            } else {
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            }
        } else {
            // Authentification échouée
            req.setAttribute("errorMessage", "Identifiants invalides.");
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
        }
    }

    private void handleLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Invalider la session pour déconnecter l'utilisateur
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + "/home"); // Changez cette URL si nécessaire
    }

    @Override
    public void destroy() {
        userRepository.close();
    }
}
