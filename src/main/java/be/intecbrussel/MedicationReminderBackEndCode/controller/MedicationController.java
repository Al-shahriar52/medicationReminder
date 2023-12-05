package be.intecbrussel.MedicationReminderBackEndCode.controller;

import be.intecbrussel.MedicationReminderBackEndCode.exception.MedicationNotFoundException;
import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.Medication;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.MedicationRequest;
import be.intecbrussel.MedicationReminderBackEndCode.repository.UserRepository;
import be.intecbrussel.MedicationReminderBackEndCode.service.MedicationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/medication")
public class MedicationController {

    private final MedicationService medicationService;
    private final UserRepository userRepository;

    // constructors
    public MedicationController(MedicationService medicationService, UserRepository userRepository) {
        this.medicationService = medicationService;
        this.userRepository = userRepository;

    }
    // custom methods
    @PostMapping("/user/{userEmail}/addMedication")
    public ResponseEntity<MedicationRequest> addMedication(@RequestBody MedicationRequest medicationRequest,
                                                    @PathVariable String userEmail) {
        try {

            MedicationRequest newMedication = medicationService.addMedication(medicationRequest, userEmail);
            return new ResponseEntity<>(newMedication, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error while adding medication", e);
        }
    }
    @PutMapping("/updateMedication/{id}")
    public ResponseEntity<Medication> updateMedication(@PathVariable Long id, @RequestBody Medication medicationDetails) {
        try {
            Medication updatedMedication = medicationService.updateMedication(id, medicationDetails);
            return ResponseEntity.ok(updatedMedication);
        } catch (MedicationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error updating medication", e);
        }
    }
//    @GetMapping("/dueReminders")
//    public ResponseEntity<List<Medication>> getDueReminders() {
//        try {
//            List<Medication> dueMedications = medicationService.getDueMedicationsForReminder();
//            return ResponseEntity.ok(dueMedications);
//        } catch (Exception e) {
//            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error retrieving due reminders", e);
//        }
//    }


    @GetMapping("/user/{email}")
    public ResponseEntity<List<Medication>> getAllMedicationsForUser(@PathVariable String email) {
        try {
            List<Medication> medications = medicationService.getAllMedicationsForUser(email);
            return ResponseEntity.ok(medications);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Error while retrieving medications", e);
        }
    }


    @DeleteMapping("/{deleteMedication}")
    public ResponseEntity<?> deleteMedication(@RequestParam Long id) {
        try {
            medicationService.deleteMedication(id);
            return ResponseEntity.ok(Map.of("message", "Medication deleted successfully"));
        } catch (MedicationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while deleting medication", e);
        }
    }

}