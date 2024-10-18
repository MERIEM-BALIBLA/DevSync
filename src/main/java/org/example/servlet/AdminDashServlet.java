package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.Task;
import org.example.model.User;
import org.example.repository.implementation.TagRepository;
import org.example.repository.implementation.UserRepository;
import org.example.service.*;

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
        TagRepository tagRepository = new TagRepository();
        this.tagService = new TagService(tagRepository);
        UserRepository userRepository = new UserRepository();
        TokenService tokenService = new TokenService();
        this.userService = new UserService(userRepository, tokenService);
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
