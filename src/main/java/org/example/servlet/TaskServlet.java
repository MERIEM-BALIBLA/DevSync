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
import org.example.repository.implementation.TagRepository;
import org.example.repository.implementation.UserRepository;
import org.example.service.TagService;
import org.example.service.TaskService;
import org.example.service.TokenService;
import org.example.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    TaskService taskService;
    TagService tagService;
    UserService userService;

    public TaskServlet() {
        this.taskService = new TaskService();
        UserRepository userRepository = new UserRepository();
        TokenService tokenService = new TokenService();
        this.userService = new UserService(userRepository, tokenService);
        TagRepository tagRepository = new TagRepository();
        this.tagService = new TagService(tagRepository);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("insertTask".equals(action)) {
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);

            req.getRequestDispatcher("/vue/admin/task/addTask.jsp").forward(req, resp);
        } else if ("editTask".equals(action)) {

            String id = req.getParameter("id");
            Task editTask = taskService.findById(Long.valueOf(id));
            req.setAttribute("task", editTask);
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);

            req.getRequestDispatcher("/vue/admin/task/editTask.jsp").forward(req, resp);

        } else {
            List<Task> tasks = taskService.getAllTask();
            req.setAttribute("tasks", tasks);

            req.getRequestDispatcher("/vue/admin/task/TaskList.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("insertTask".equals(action)) {
            insertTask(req, resp);
        }
        if ("delete".equalsIgnoreCase(action)) {
            delete(req, resp);
        }
        if ("editTask".equals(action)) {
            editTask(req, resp);
        }
    }

    protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("taskId");
        if (id != null) {
            taskService.deleteTask(Long.parseLong(id));
            resp.sendRedirect(req.getContextPath() + "/tasks");
        }
    }

    private void insertTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String endDateStr = req.getParameter("endDate");
        String startDateStr = req.getParameter("startDate");
        String assignedUserIdStr = req.getParameter("assignedUserId");

        String[] tags = req.getParameter("tag").split("\\s*,\\s*");
        List<Tag> tagsList = new ArrayList<>();

        for (String titleTag : tags) {
            Tag tag = tagService.findByTitle(titleTag);
            if (tag == null) {
                tag = new Tag(titleTag);
            }
            tagsList.add(tag);
        }
        User assignedUser = userService.findById(Integer.parseInt(assignedUserIdStr));
        LocalDate endDate = LocalDate.parse(endDateStr);
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
        newTask.setAssignedUser(assignedUser);
        newTask.setCreatedBy(sessionUser);
        newTask.setTags(tagsList);

        try {
            taskService.validateTask(newTask);
            taskService.save(newTask);
            resp.sendRedirect(req.getContextPath() + "/tasks");
        } catch (IllegalArgumentException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.setAttribute("task", newTask);
            req.getRequestDispatcher("/vue/admin/task/AddTask.jsp").forward(req, resp);
        }
    }

    private void editTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String taskId = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String endDate = req.getParameter("endDate");
        String assignedUserId = req.getParameter("assignedUserId");

        String[] tags = req.getParameter("tag").split("\\s*,\\s*"); // Split tags by commas and trim spaces
        List<Tag> tagsList = new ArrayList<>();

        for (String titleTag : tags) {
            Tag tag = tagService.findByTitle(titleTag);
            if (tag == null) {
                tag = new Tag(titleTag);
            }
            tagsList.add(tag);
        }
        long id = Long.parseLong(taskId);
        Task task = taskService.findById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setEndDate(LocalDate.parse(endDate));
        task.setTags(tagsList);

        UserRepository userRepository = new UserRepository();
        TokenService tokenService = new TokenService();
        UserService userService = new UserService(userRepository, tokenService);
        User assignedUser = userService.findById(Integer.parseInt(assignedUserId));

        task.setAssignedUser(assignedUser);
        Task updatedTask = taskService.updateTask(task);

        if (updatedTask != null) {
            resp.sendRedirect(req.getContextPath() + "/tasks?action=list");
        } else {
            req.setAttribute("errorMessage", "Échec de la mise à jour de la tâche.");
            req.getRequestDispatcher("/vue/task/error.jsp").forward(req, resp);
        }
    }

}
