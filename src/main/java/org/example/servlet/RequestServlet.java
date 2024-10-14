
package org.example.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Request;
import org.example.model.Task;
import org.example.model.User;
import org.example.model.enums.RequetStatus;
import org.example.service.RequestService;
import org.example.service.UserService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "request", value = "/request")
public class RequestServlet extends HttpServlet {

    RequestService requestService;
    UserService userService;

    public RequestServlet() {
        this.requestService = new RequestService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Request> requests = requestService.getAll();
            req.setAttribute("requests", requests);
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/vue/admin/task/RequestList.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int requestId = Integer.parseInt(req.getParameter("requestId"));
            int assignedUserId = Integer.parseInt(req.getParameter("assignedUserId"));
            String statusParam = req.getParameter("status");

            System.out.println(requestId);
            System.out.println(assignedUserId);
            System.out.println(statusParam);
            if (statusParam != null) {
                RequetStatus status = RequetStatus.valueOf(statusParam);

                requestService.acceptRequest(requestId, assignedUserId, status);

                resp.sendRedirect(req.getContextPath() + "/request");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Status parameter is missing.");
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request or user ID.");
        } catch (IllegalArgumentException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid status value.");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

}
