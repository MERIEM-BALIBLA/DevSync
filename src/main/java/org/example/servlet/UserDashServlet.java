package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.model.enums.Role;

import java.io.IOException;

@WebServlet("/dashboard")
public class UserDashServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Vérifier la session pour l'utilisateur connecté
        HttpSession session = req.getSession(false); // Ne pas créer une nouvelle session
        if (session == null || session.getAttribute("user") == null) {
            // L'utilisateur n'est pas connecté, rediriger vers la page de connexion
            resp.sendRedirect(req.getContextPath() + "/auth?action=login"); // Modifiez le chemin selon vos besoins
            return;
        }

        // Récupérer l'utilisateur de la session
        User user = (User) session.getAttribute("user");

        // Vérifier si l'utilisateur a le rôle "USER"
        if (user.getRole() == Role.USER) {
            // Si l'utilisateur a le bon rôle, afficher le tableau de bord
            try {
                req.getRequestDispatcher("/vue/Dash.jsp").forward(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur est survenue.");
            }
        } else {
            // L'utilisateur n'a pas le bon rôle, rediriger ou afficher un message d'erreur
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
        }
    }
}
