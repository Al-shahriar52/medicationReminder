package be.intecbrussel.MedicationReminderBackEndCode.controller;

import be.intecbrussel.MedicationReminderBackEndCode.service.ReminderSchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reminders")
public class ReminderController {

    @Autowired
    private ReminderSchedulerService reminderService;

    @PostMapping("/send")
    public ResponseEntity<String> sendRemindersManually() {
        reminderService.checkReminders();
        return ResponseEntity.ok("Reminders sent successfully");
    }
}
