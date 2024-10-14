package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.Task;
import org.example.model.User;
import org.example.service.RequestService;
import org.example.service.TagService;
import org.example.service.TaskService;
import org.example.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin_dashboard")
public class AdminDashServlet extends HttpServlet {
    TaskService taskService;
    TagService tagService;
    UserService userService;
    RequestService requestService;

    public AdminDashServlet() {
        this.taskService = new TaskService();
        this.tagService = new TagService();
        this.userService = new UserService();
        this.requestService = new RequestService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Task> tasks = taskService.getTasksByManager();
        req.setAttribute("tasks", tasks);
        int countTask = taskService.count(tasks);
        int conutTag = tagService.count();
        int countUser = userService.count();
        int countRequest = requestService.count();
        req.getRequestDispatcher("/vue/admin/dashboard.jsp").forward(req, resp);
    }
}
