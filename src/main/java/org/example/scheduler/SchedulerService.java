package org.example.scheduler;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import org.example.model.Token;
import org.example.repository.implementation.TaskRepository;
import org.example.repository.implementation.TokenRepository;
import java.util.List;

@Singleton
@Startup
public class SchedulerService {

    private final TokenRepository tokenRepository;

    public SchedulerService() {
        TaskRepository taskRepository = new TaskRepository();
        this.tokenRepository = new TokenRepository();
    }

    @Schedule(hour = "0", minute = "0", second = "0", persistent = false)

    public void resetDailyJetons() {
        List<Token> allJetons = tokenRepository.getAll();
        for (Token Jeton : allJetons) {
            Jeton.setDailyTokens(2);
            tokenRepository.save(Jeton);
        }
    }

    @Schedule(hour = "0", minute = "0", dayOfMonth = "1", persistent = false)
    public void resetMonthlyJetons() {
        List<Token> allJetons = tokenRepository.getAll();
        for (Token token : allJetons) {
            token.setMonthlyTokens(1);
            tokenRepository.save(token);
        }
    }

}
