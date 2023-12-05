package be.intecbrussel.MedicationReminderBackEndCode.model.dto;

import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.Medication;
import lombok.Data;

@Data
public class MedicationRequest {

    private Long id;
    private String name;
    private String dosage;
    private String frequency;
}
