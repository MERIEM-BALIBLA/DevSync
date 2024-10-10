package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.Tag;
import org.example.model.Task;
import org.example.model.User;
import org.example.model.enums.Role;
import org.example.repository.implementation.TagRepository;
import org.example.service.TagService;
import org.example.service.TaskService;
import org.example.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet("/dashboard")
public class UserDashServlet extends HttpServlet {
    private final TaskService taskService;
    private TagService tagService;

    public UserDashServlet() {
        this.taskService = new TaskService();
        this.tagService = new TagService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/auth?action=login");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = req.getParameter("action"); // Retrieve action parameter

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
        if (action.equals("InsertTask")) {
            insertTask(req, resp);
        } else if ("Delete".equals(action)) {
            doDelete(req, resp);
        }
    }

    private void insertTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String endDateStr = req.getParameter("endDate");
        String[] existingTags = req.getParameterValues("existingTags[]");
        if (existingTags == null) {
            System.out.println("existingTags is null");
        } else {
            System.out.println("existingTags size: " + existingTags.length);
        }
        String newTag = req.getParameter("newTag"); // Récupérer le nouveau tag

        // Validation des paramètres
        if (title == null || title.isEmpty() || description == null || description.isEmpty() || endDateStr == null) {
            req.setAttribute("errorMessage", "Tous les champs doivent être remplis.");
            req.getRequestDispatcher("/vue/user/InsertTask.jsp").forward(req, resp);
            return;
        }

        LocalDate endDate;
        try {
            endDate = LocalDate.parse(endDateStr);
            // Vérification que la date de fin est après aujourd'hui
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
        newTask.setDate(endDate);
        newTask.setAssignedUser(sessionUser);
        newTask.setCreatedBy(sessionUser);

        List<Tag> tagsToAdd = new ArrayList<>();

        newTask.setTags(tagsToAdd);
        Task task = taskService.insertTask(newTask);
        resp.sendRedirect(req.getContextPath() + "/dashboard");
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("taskId");
        if (id != null) {
            taskService.deleteTask(Integer.parseInt(id));
            resp.sendRedirect(req.getContextPath() + "/dashboard");

        }
    }

}
