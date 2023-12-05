package be.intecbrussel.MedicationReminderBackEndCode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedicationReminderBackEndCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicationReminderBackEndCodeApplication.class, args);
	}

}
