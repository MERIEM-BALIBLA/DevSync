package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.Task;
import org.example.model.User;
import org.example.repository.implementation.UserRepository;
import org.example.service.TaskService;
import org.example.service.UserService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/tasks")
public class TaskServlet extends HttpServlet {
    TaskService taskService;
    UserService userService;

    public TaskServlet() {
        this.taskService = new TaskService();
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("insertTask".equals(action)) {
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);

            req.getRequestDispatcher("/vue/task/addTask.jsp").forward(req, resp);
        } else if ("editTask".equals(action)) {

            String id = req.getParameter("id");
            Task editTask = taskService.findById(Integer.parseInt(id));
            req.setAttribute("task", editTask);
            List<User> users = userService.getAllUsers();
            req.setAttribute("users", users);

            req.getRequestDispatcher("/vue/task/editTask.jsp").forward(req, resp);

        } else {
            List<Task> tasks = taskService.getAllTask();
            req.setAttribute("tasks", tasks);

            req.getRequestDispatcher("/vue/admin/TaskList.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("insertTask".equals(action)) {
            isertTask(req, resp);
        }
        if ("delete".equalsIgnoreCase(action)) {
            doDelete(req, resp);
        }
        if ("editTask".equals(action)) {
            editTask(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("taskId");
        if (id != null) {
            taskService.deleteTask(Integer.parseInt(id));
            resp.sendRedirect(req.getContextPath() + "/tasks");

        }
    }

    private void isertTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String endDateStr = req.getParameter("endDate");
        String assignedUserIdStr = req.getParameter("assignedUserId");

        // Validation des paramètres
        if (title == null || title.isEmpty() || description == null || description.isEmpty() ||
                endDateStr == null || assignedUserIdStr == null) {
            req.setAttribute("errorMessage", "Tous les champs doivent être remplis.");
            req.getRequestDispatcher("/vue/auth/ajouterTask.jsp").forward(req, resp);
            return;
        }

        // Récupération de l'utilisateur assigné
        int assignedUserId;
        try {
            assignedUserId = Integer.parseInt(assignedUserIdStr);
        } catch (NumberFormatException e) {
            req.setAttribute("errorMessage", "L'ID de l'utilisateur doit être un nombre valide.");
            req.getRequestDispatcher("/vue/auth/ajouterTask.jsp").forward(req, resp);
            return;
        }

        UserService userService = new UserService();
        User assignedUser = userService.findById(assignedUserId);
        if (assignedUser == null) {
            req.setAttribute("errorMessage", "Utilisateur non trouvé.");
            req.getRequestDispatcher("/vue/auth/ajouterTask.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession();
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null) {
            req.setAttribute("errorMessage", "Utilisateur non authentifié.");
            req.getRequestDispatcher("/vue/auth/ajouterTask.jsp").forward(req, resp);
            return;
        }

        // Création de la tâche
        LocalDate endDate = LocalDate.parse(endDateStr);
        Task newTask = new Task(title, description, endDate, assignedUser, sessionUser);

        // Débogage
        System.out.println("Inserting task: " + newTask);

        try {
            taskService.insertTask(newTask);
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Erreur lors de l'insertion de la tâche.");
            req.getRequestDispatcher("/vue/auth/ajouterTask.jsp").forward(req, resp);
            return;
        }

        resp.sendRedirect(req.getContextPath() + "/tasks");
    }


    private void editTask(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String taskId = req.getParameter("id");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String endDate = req.getParameter("endDate");
        String assignedUserId = req.getParameter("assignedUserId");

        if (taskId == null || taskId.isEmpty()) {
            req.setAttribute("errorMessage", "ID de tâche invalide.");
            req.getRequestDispatcher("/vue/task/error.jsp").forward(req, resp);
            return;
        }

        long id = Long.parseLong(taskId);

        // Trouver la tâche existante
        TaskService taskService = new TaskService();
        Task task = taskService.findById((int) id);

        if (task == null) {
            req.setAttribute("errorMessage", "Tâche non trouvée.");
            req.getRequestDispatcher("/vue/task/error.jsp").forward(req, resp);
            return;
        }

        // Mise à jour des informations de la tâche
        task.setTitle(title);
        task.setDescription(description);
        task.setDate(LocalDate.parse(endDate));

        // Vérification de l'utilisateur assigné
        UserService userService = new UserService();
        User assignedUser = userService.findById(Integer.parseInt(assignedUserId));
        if (assignedUser == null) {
            req.setAttribute("errorMessage", "Utilisateur non trouvé.");
            req.getRequestDispatcher("/vue/task/error.jsp").forward(req, resp);
            return;
        }

        task.setAssignedUser(assignedUser);

        // Mettre à jour la tâche
        Task updatedTask = taskService.updateTask(task);

        if (updatedTask != null) {
            resp.sendRedirect(req.getContextPath() + "/tasks?action=list");
        } else {
            req.setAttribute("errorMessage", "Échec de la mise à jour de la tâche.");
            req.getRequestDispatcher("/vue/task/error.jsp").forward(req, resp);
        }
    }

}
