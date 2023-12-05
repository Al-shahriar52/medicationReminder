package be.intecbrussel.MedicationReminderBackEndCode.service;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class NotificationService {
    private static final Logger LOGGER = Logger.getLogger(NotificationService.class.getName());

    public void sendNotification(String message, String recipientEmail) {
        // Logic to send an email or other types of notifications
        // This is a placeholder; integrate with your email service or notification system
        LOGGER.info("Sending notification to " + recipientEmail + ": " + message);
    }
}
