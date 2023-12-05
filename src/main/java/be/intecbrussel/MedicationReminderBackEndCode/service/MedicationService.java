package be.intecbrussel.MedicationReminderBackEndCode.service;
import be.intecbrussel.MedicationReminderBackEndCode.exception.MedicationNotFoundException;
import be.intecbrussel.MedicationReminderBackEndCode.exception.ResourceNotFound;
import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.Medication;
import be.intecbrussel.MedicationReminderBackEndCode.model.MedicationSchedule;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.MedicationRequest;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.MedicationScheduleDTO;
import be.intecbrussel.MedicationReminderBackEndCode.repository.MedicationRepository;
import be.intecbrussel.MedicationReminderBackEndCode.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    private final MedicationRepository medicationRepository;
    private final UserRepository userRepository;

    // constructors
    public MedicationService(MedicationRepository medicationRepository, UserRepository userRepository) {
        this.medicationRepository = medicationRepository;
        this.userRepository = userRepository;
    }

    public MedicationRequest addMedication(MedicationRequest medication, String userEmail) {

        Medication newMedication = convertToEntity(medication);


        AppUser user = userRepository.findById(userEmail).orElseThrow(()->
                new ResourceNotFound("AppUser", "email",userEmail));

        newMedication.setUser(user);
        newMedication.setDosage(medication.getDosage());
        newMedication.setFrequency(medication.getFrequency());
        newMedication.setName(medication.getName());

        return convertToDTO(medicationRepository.save(newMedication));
    }

    public List<Medication> getAllMedicationsForUser(String email) {
        return medicationRepository.findAllByUserEmail(email);
    }
    public Medication updateMedication(Long id, Medication medicationDetails) throws MedicationNotFoundException {
        Medication medication = medicationRepository.findById(id)
                .orElseThrow(() -> new MedicationNotFoundException("Medication not found for this id :: " + id));

        // Update the medication details here
        medication.setName(medicationDetails.getName());
        medication.setDosage(medicationDetails.getDosage());
        medication.setFrequency(medicationDetails.getFrequency());

        return medicationRepository.save(medication);
    }


    public void deleteMedication(Long id) throws MedicationNotFoundException {
        if (medicationRepository.existsById(id)) {
            medicationRepository.deleteById(id);
        } else {
            throw new MedicationNotFoundException("Medication not found with id :" + id);
        }
    }

    private MedicationRequest convertToDTO(Medication medication) {

        MedicationRequest medicationRequest = new MedicationRequest();
        medicationRequest.setId(medication.getId());
        medicationRequest.setName(medication.getName());
        medicationRequest.setDosage(medication.getDosage());
        medicationRequest.setFrequency(medication.getFrequency());
        return medicationRequest;
    }

    private Medication convertToEntity(MedicationRequest medicationRequest) {

        Medication medication = new Medication();
        medication.setId(medicationRequest.getId());
        medication.setName(medicationRequest.getName());
        medication.setDosage(medicationRequest.getDosage());
        medication.setFrequency(medicationRequest.getFrequency());
        return medication;
    }
//    public List<Medication> getDueMedicationsForReminder() {
//        Date currentTime = new Date();
//        return medicationRepository.findByReminderEnabledTrueAndNextReminderTimeBefore(currentTime);
//    }
//    private Date calculateNextReminderTime(Medication medication){
//        String frequency = medication.getFrequency();
//        int hours = extractHoursFromFrequency(frequency);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.HOUR,hours);
//        return  calendar.getTime();
//    }
    //    private int extractHoursFromFrequency(String frequency) {
//        // Implement logic to parse the frequency string and extract hours
//        // This is a placeholder example
//        if(frequency.equals("Every 6 hours")) {
//            return 6;
//        }
//        // Add more conditions for different frequencies
//        return 0; // Default value if no matching condition is found
//    }
//    private int extractHoursFromFrequency(String frequency) {
//        // Basic implementation - tailor this to your actual frequency formats
//        String[] parts = frequency.split(" ");
//        try {
//            if (parts[0].equalsIgnoreCase("Every") && parts.length == 3) {
//                return Integer.parseInt(parts[1]); // Converts the string number to an integer
//            }
//            // Add more conditions as needed
//        } catch (NumberFormatException e) {
//            // Handle the case where the string cannot be converted to a number
//            System.err.println("Frequency format error: " + e.getMessage());
//        }
//        return 0; // Default or error case
//    }

//    @Scheduled(fixedRate = 60000) // For example, every 60 seconds
//    public void sendReminders() {
//        List<Medication> dueMedications = getDueMedicationsForReminder();
//        for (Medication medication : dueMedications) {
//            // Logic to send reminders (e.g., push notification, email)
//            System.out.println("Sending reminder for medication: " + medication.getName());
//
//            // Update next reminder time
//            Date nextReminder = calculateNextReminderTime(medication);
//            medication.setNextReminderTime(nextReminder);
//            medicationRepository.save(medication);
//        }
    }
