package org.example.scheduler;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.example.model.Request;
import org.example.model.Token;
import org.example.model.User;
import org.example.service.RequestService;
import org.example.service.TokenService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@Startup
public class RequestScheduler {

    private final RequestService requestService;
    private final TokenService tokenService;

    public RequestScheduler() {
        this.requestService = new RequestService();
        this.tokenService = new TokenService();
    }

    @Schedule(second = "*/30", minute = "*", hour = "*", persistent = false)
    public void checkPendingRequests() {
        List<Request> pendingRequests = requestService.getAll();

        LocalDateTime now = LocalDateTime.now();
        for (Request request : pendingRequests) {
            if (request.getCreatedAt().plusDays(1).isBefore(LocalDate.now())) {
                User user = request.getUser();
                Token userToken = user.getToken();
                userToken.setDailyTokens(userToken.getDailyTokens() * 2);
                tokenService.update(userToken);

                System.out.println("Doublé les jetons de l'utilisateur : " + user.getUsername());
            }
        }
    }
}
