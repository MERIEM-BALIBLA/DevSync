package org.example.service;

import org.example.model.Request;
import org.example.model.Task;
import org.example.model.Token;
import org.example.model.User;
import org.example.model.enums.RequetStatus;
import org.example.repository.implementation.RequestRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RequestService {
    private final RequestRepository requestRepository;
    private final TaskService taskService;
    private final UserService userService;
    private final TokenService tokenService;

    public RequestService() {
        this.requestRepository = new RequestRepository();
        this.taskService = new TaskService();
        this.userService = new UserService();
        this.tokenService = new TokenService();
    }

    public Request save(Request request) {
        Optional<Request> existingRequest = this.findByTaskId(Math.toIntExact(request.getTask().getId()));
        if (existingRequest.isPresent()) {
            throw new IllegalArgumentException("A request already exists for this task ID: " + request.getTask().getId());
        }
        return requestRepository.save(request);
    }

/*
    public Request save(Request request) {
        Optional<Request> existingRequest = requestRepository.findByTaskId(Math.toIntExact(request.getTask().getId()));
        if (existingRequest.isPresent()) {
            throw new IllegalArgumentException("A request already exists for this task ID: " + request.getTask().getId());
        }
        User user = request.getTask().getAssignedUser();
        Token userTokens = tokenService.findByUser(user);
        if (userTokens == null || userTokens.getDailyTokens() <= 0) {
            throw new IllegalStateException("User does not have enough tokens to make a request.");
        }
        userTokens.setDailyTokens(userTokens.getDailyTokens() - 1);
        tokenService.update(userTokens);
        return requestRepository.save(request);
    }
*/


    public List<Request> getAll() {
        return requestRepository.getAll().stream()
                .filter(req -> req.getStatus().equals(RequetStatus.PENDING))
                .collect(Collectors.toList());
    }

    public Optional<Request> findByTaskId(long id) {
        return requestRepository.findByTaskId(id);
    }

    public Request update(Request request) {
        return requestRepository.update(request);
    }

    public Optional<Request> findById(int id) {
        return requestRepository.findById(id);
    }

    public void acceptRequest(int requestId, int assignedUserId, RequetStatus status) throws SQLException {
        Optional<Request> requestItem = this.findById(requestId);

        if (requestItem.isPresent()) {
            Request request = requestItem.get();
            User user = request.getUser();

            request.setStatus(status);
            if (status == RequetStatus.APPROVED) {
                Task task = taskService.findById(request.getTask().getId());
                if (task != null) {
                    User assignedUser = userService.findById(assignedUserId);
                    if (assignedUser != null) {
                        task.setAssignedUser(assignedUser);
                        taskService.updateTask(task);

                        Token dailyToken = user.getToken();
                        dailyToken.setDailyTokens(dailyToken.getDailyTokens() - 1);
                        tokenService.update(dailyToken);
                        System.out.println("Token decremented for user: " + user.getUsername());

                        System.out.println("Assigned user updated to: " + task.getAssignedUser().getUsername());
                    } else {
                        System.out.println("Assigned user not found for ID: " + assignedUserId);
                    }
                }
            }
            this.update(request); // Mettre à jour la demande
        } else {
            System.out.println("Request not found for ID: " + requestId);
        }
    }


    /*
        public void acceptRequest(int requestId, int assignedUserId, RequetStatus status) throws SQLException {
            Optional<Request> requestItem = this.findById(requestId);
            Request request = requestItem.get();
            User user = request.getUser();

            request.setStatus(status);
            if (status == RequetStatus.APPROVED) {

                Task task = taskService.findById(Math.toIntExact(request.getTask().getId()));
                if (task != null) {
                    User assignedUser = userService.findById(assignedUserId);
                    if (assignedUser != null) {
                        task.setAssignedUser(assignedUser);
                        taskService.updateTask(task);
                        tokenService.save(user.getToken().setDailyTokens(-1));
                        System.out.println("Assigned user updated to: " + task.getAssignedUser().getUsername());
                    } else {
                        System.out.println("Assigned user not found for ID: " + assignedUserId);
                    }
                }
            }
            this.update(requestItem.get());
        }
    */
    public int count() {
        return this.getAll().size();
    }
}
