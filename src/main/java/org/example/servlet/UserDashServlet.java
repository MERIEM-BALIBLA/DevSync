package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.*;
import org.example.service.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebServlet("/dashboard")
public class UserDashServlet extends HttpServlet {
    private final TaskService taskService;
    private final TagService tagService;
    private final RequestService requestService;
    private final TokenService tokenService;

    public UserDashServlet() {
        this.taskService = new TaskService();
        this.tagService = new TagService();
        this.requestService = new RequestService();
        this.tokenService = new TokenService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action");

        if (user.isManager()) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès interdit.");
            return;
        }

        try {
            if ("InsertTask".equals(action)) {
                List<Tag> tags = tagService.getAllTag();
                req.setAttribute("tags", tags);
                req.getRequestDispatcher("/vue/user/InsertTask.jsp").forward(req, resp);
            } else {
                List<Task> tasks = taskService.getTasksByUserId(user.getId());
                req.setAttribute("tasks", tasks);
                List<Task> SousTasks = taskService.getSubTasks(user.getId());
                req.setAttribute("sousTasks", SousTasks);

                req.getRequestDispatcher("/vue/user/Dash.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Une erreur est survenue.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action) {
            case "InsertTask":
                insertTask(req, resp);
                break;
            case "Delete":
                delete(req, resp);
                break;
            case "updateStatus":
                updateStatus(req, resp);
                break;
        }
        if ("refuseTask".equals(action)) {
            sendRequest(req, resp);
        }


    }

    private void insertTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String endDateStr = req.getParameter("endDate");
        String startDateStr = req.getParameter("startDate");

        String[] tags = req.getParameter("tag").split("\\s*,\\s*"); // Split tags by commas and trim spaces
        List<Tag> tagsList = new ArrayList<>();

        // Retrieve tags and check for null
        for (String titleTag : tags) {
            Tag tag = tagService.findByTitle(titleTag);
            if (tag == null) {
                tag = new Tag(titleTag);
            }
            tagsList.add(tag); // Only add valid tags
        }

        System.out.println(tagsList);

        if (title == null || title.isEmpty() || description == null || description.isEmpty() || endDateStr == null) {
            req.setAttribute("errorMessage", "Tous les champs doivent être remplis.");
            req.getRequestDispatcher("/vue/user/InsertTask.jsp").forward(req, resp);
            return;
        }

        LocalDate endDate;
        try {
            endDate = LocalDate.parse(endDateStr);
            if (endDate.isBefore(LocalDate.now())) {
                req.setAttribute("errorMessage", "La date de fin doit être après aujourd'hui.");
                req.getRequestDispatcher("/vue/user/InsertTask.jsp").forward(req, resp);
                return;
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Date de fin invalide.");
            req.getRequestDispatcher("/vue/user/InsertTask.jsp").forward(req, resp);
            return;
        }
        LocalDate startDate = LocalDate.parse(startDateStr);
        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            req.setAttribute("errorMessage", "Utilisateur non authentifié.");
            req.getRequestDispatcher("/vue/user/InsertTask.jsp").forward(req, resp);
            return;
        }

        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(description);
        newTask.setStartDate(startDate);
        newTask.setEndDate(endDate);
        newTask.setAssignedUser(sessionUser);
        newTask.setCreatedBy(sessionUser);
        newTask.setTags(tagsList);

        taskService.save(newTask);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    protected void updateStatus(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String taskIdStr = req.getParameter("taskId");
        String status = req.getParameter("status");  // "completed" ou "undo"

        if (taskIdStr != null && !taskIdStr.isEmpty()) {
            int taskId = Integer.parseInt(taskIdStr);
            Task task = taskService.findById(taskId);

            if (task != null) {
                LocalDate currentDate = LocalDate.now();
                LocalDate deadline = task.getEndDate();

                if ("completed".equals(status)) {
                    if (deadline != null && currentDate.isBefore(deadline)) {
                        task.setCompleted(true);
                    } else {
                        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task cannot be marked as completed because the deadline has passed");
                        return;
                    }
                } else if ("undo".equals(status)) {
                    task.setCompleted(false);
                }
                taskService.updateStatus(task);
                resp.sendRedirect(req.getContextPath() + "/dashboard");
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Task not found");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid task ID");
        }
    }

    protected void sendRequest(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String taskId = req.getParameter("taskId");
        Task task = taskService.findById((int) Long.parseLong(taskId));
        if (task != null) {
            User user = task.getAssignedUser();

            if (user.getToken().getDailyTokens() > 0) {
                Request request = new Request();
                request.setUser(user);
                request.setTask(task);
                requestService.save(request);
            }
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("taskId");
        if (id != null) {
            taskService.deleteTask(Integer.parseInt(id));
            resp.sendRedirect(req.getContextPath() + "/dashboard");
        }
    }

}
