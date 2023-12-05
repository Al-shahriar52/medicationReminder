package be.intecbrussel.MedicationReminderBackEndCode.utils;

import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeUtils {

    public String Convert() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");

        return dateTime.format(formatter);
    }
}
