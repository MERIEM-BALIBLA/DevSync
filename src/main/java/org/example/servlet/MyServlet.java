package org.example.servlet;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.repository.implementation.UserRepository;

import java.io.IOException;
//import jakarta.io.IOException;

@WebServlet(name = "home", value = "/home") // Changez ici
public class MyServlet extends HttpServlet {
    String message = "";

    public void init() throws ServletException {
        message = "Hello World!";
        try {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("myJPAUnit");
            EntityManager entityManager = emf.createEntityManager();
            if (entityManager.isOpen())
                message = "Connected to the database successfully!";
            else
                message = "Not connected..";
        } catch (Exception e) {
            message = "Error connecting : " + e.getMessage();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
//        user.setUsername("Chaima");
//        user.setEmail("chaima@gmail.com");
        UserRepository userRepository = new UserRepository();

        try {
//            userRepository.insertUser(user);
            req.getRequestDispatcher("/home.jsp").forward(req, resp);
        } catch (Exception e) {
//            resp.getWriter().println("<h1>Error while inserting user: " + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }

}
