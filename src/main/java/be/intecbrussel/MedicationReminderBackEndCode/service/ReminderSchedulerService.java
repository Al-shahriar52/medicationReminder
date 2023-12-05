package be.intecbrussel.MedicationReminderBackEndCode.service;

import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.Medication;
import be.intecbrussel.MedicationReminderBackEndCode.model.MedicationSchedule;
import be.intecbrussel.MedicationReminderBackEndCode.repository.MedicationScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReminderSchedulerService {

    @Autowired
    private MedicationScheduleService medicationScheduleService;

    @Autowired
    private NotificationService notificationService;

    @Scheduled(fixedRate = 60000) // Run every minute (adjust the rate as needed)
    //@Scheduled(cron = "0 0/5 * * * *") // Cron expression to check every 5 minutes
    public void checkReminders() {
        medicationScheduleService.checkAndSendReminders();
    }
}
