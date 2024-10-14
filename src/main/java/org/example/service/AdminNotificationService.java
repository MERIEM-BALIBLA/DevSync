package org.example.service;

import org.example.model.Task;

public class AdminNotificationService {

    public void notifyAdmin(Task task, String reason) {
        String adminEmail = "admin@example.com"; // Adresse email de l'administrateur
        String subject = "Task Refusal Notification";
        String message = String.format(
                "User has refused the task: %s\nReason: %s\nTask ID: %s",
                task.getTitle(),
                reason,
                task.getId()
        );

        // Code pour envoyer l'e-mail (peut utiliser JavaMail ou une autre bibliothèque)
        sendEmail(adminEmail, subject, message);
    }

    private void sendEmail(String to, String subject, String message) {
        // Implémentation pour envoyer un email
        // Par exemple, en utilisant JavaMail API
    }
}
