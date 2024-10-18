package org.example.scheduler;

import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.time.temporal.ChronoUnit;
import org.example.model.Token;
import org.example.repository.implementation.TokenRepository;

@WebListener
public class TokenSchedulerListener implements ServletContextListener {
    private static final Logger LOGGER = Logger.getLogger(TokenSchedulerListener.class.getName());
    private ScheduledExecutorService scheduler;
    private TokenRepository tokenRepository;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Initializing Token Scheduler");
        scheduler = Executors.newScheduledThreadPool(2);
        tokenRepository = new TokenRepository();

        // Test: Schedule daily reset every 30 seconds
        scheduleDailyReset();

        // Monthly reset remains at midnight on first day of month
        scheduleMonthlyReset();
    }

    private void scheduleDailyReset() {
        LOGGER.info("Starting daily token reset scheduler (30-second intervals for testing)");

        scheduler.scheduleAtFixedRate(
                () -> {
                    try {
                        LOGGER.info("Starting token reset process at: " + LocalDateTime.now());
                        List<Token> tokens = tokenRepository.getAll();
                        LOGGER.info("Found " + tokens.size() + " tokens to reset");

                        if (tokens.isEmpty()) {
                            LOGGER.warning("No tokens found in database!");
                            return;
                        }

                        for (Token token : tokens) {
                            LOGGER.info("Current daily tokens for user " + token.getUser().getUsername()
                                    + ": " + token.getDailyTokens());
                            token.setDailyTokens(2);
                            // Use update instead of save since tokens already exist
                            tokenRepository.update(token);
                            LOGGER.info("Successfully reset daily tokens to 2 for user "
                                    + token.getUser().getUsername());
                        }
                        LOGGER.info("Token reset process completed successfully");
                    } catch (Exception e) {
                        LOGGER.severe("Failed to reset daily tokens. Error: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                0,
                30,
                TimeUnit.SECONDS
        );
    }

    private void scheduleMonthlyReset() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withDayOfMonth(1).plusMonths(1).withHour(0).withMinute(0).withSecond(0);
        long initialDelay = ChronoUnit.SECONDS.between(now, nextRun);

        scheduler.scheduleAtFixedRate(
                () -> {
                    try {
                        LOGGER.info("Executing monthly token reset");
                        List<Token> tokens = tokenRepository.getAll();
                        for (Token token : tokens) {
                            token.setMonthlyTokens(1);
                            // Use update instead of save for monthly reset too
                            tokenRepository.update(token);
                            LOGGER.info("Reset monthly tokens for user: " + token.getUser().getUsername());
                        }
                        LOGGER.info("Monthly token reset completed");
                    } catch (Exception e) {
                        LOGGER.severe("Error during monthly token reset: " + e.getMessage());
                        e.printStackTrace();
                    }
                },
                initialDelay,
                30 * 24 * 60 * 60,
                TimeUnit.SECONDS
        );
        LOGGER.info("Monthly reset scheduled. First run in " + initialDelay + " seconds");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("Shutting down Token Scheduler");
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
            }
        }
    }
}