package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.io.IOException;
//import jakarta.io.IOException;

@WebServlet(name = "myServlet", value = "/")
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
            resp.sendRedirect("home.jsp"); // Redirige après l'insertion
        } catch (Exception e) {
//            resp.getWriter().println("<h1>Error while inserting user: " + e.getMessage() + "</h1>");
            e.printStackTrace();
        }
    }

/*
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, IOException {
        resp.setContentType("text/html");

        req.getRequestDispatcher("/home.jsp").forward(req, resp);

//
        User user = new User();
        user.setUsername("Chaima");
        user.setEmail("chaima@gmail.com");
        UserRepository userRepository = new UserRepository();
        try {
            userRepository.insertUser(user);
            resp.getWriter().println("<h1>User created successfully!" + user.getUsername() + "</h1>");
        } catch (Exception e) {
            resp.getWriter().println("<h1>Error while inserting user: " + e.getMessage() + "</h1>");
            e.printStackTrace();
        }

    }
*/
}
