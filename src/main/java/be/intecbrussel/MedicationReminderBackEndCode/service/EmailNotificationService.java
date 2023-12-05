package be.intecbrussel.MedicationReminderBackEndCode.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class EmailNotificationService {

    private final String username = "emailmedication@gmail.com"; // Replace with your email
    private final String password = "ibvseqspjdbrlaax"; // Replace with your password

    public void sendNotification(String userEmail, String message) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message mailMessage = new MimeMessage(session);
            mailMessage.setFrom(new InternetAddress(username));
            mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));
            mailMessage.setSubject("Medication Reminder");
            mailMessage.setText(message);

            Transport.send(mailMessage);

            System.out.println("Notification sent successfully to " + userEmail);
        } catch (MessagingException e) {
            System.out.println("Error sending notification: " + e.getMessage());
        }
    }
}
