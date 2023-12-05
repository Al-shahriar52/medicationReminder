package be.intecbrussel.MedicationReminderBackEndCode.service;

import be.intecbrussel.MedicationReminderBackEndCode.exception.MedicationNotFoundException;
import be.intecbrussel.MedicationReminderBackEndCode.exception.ResourceNotFound;
import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.Medication;
import be.intecbrussel.MedicationReminderBackEndCode.model.MedicationSchedule;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.MedicationScheduleDTO;
import be.intecbrussel.MedicationReminderBackEndCode.repository.MedicationRepository;
import be.intecbrussel.MedicationReminderBackEndCode.repository.MedicationScheduleRepository;
import be.intecbrussel.MedicationReminderBackEndCode.repository.UserRepository;
import be.intecbrussel.MedicationReminderBackEndCode.utils.DateTimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicationScheduleService {

    private final MedicationScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;
    private final EmailNotificationService notificationService;
    private final DateTimeUtils dateTimeUtils;

    @Autowired
    public MedicationScheduleService(MedicationScheduleRepository scheduleRepository,
                                     UserRepository userRepository,
                                     MedicationRepository medicationRepository, EmailNotificationService notificationService, DateTimeUtils dateTimeUtils) {
        this.scheduleRepository = scheduleRepository;
        this.userRepository = userRepository;
        this.medicationRepository = medicationRepository;
        this.notificationService = notificationService;
        this.dateTimeUtils = dateTimeUtils;
    }

    public MedicationScheduleDTO createMedicationSchedule(MedicationScheduleDTO schedule,
                                                       Long medicationId,
                                                       String email)
            throws MedicationNotFoundException {

        MedicationSchedule medicationSchedule = convertToEntity(schedule);

        AppUser user = userRepository.findById(email).orElseThrow(()->
                new ResourceNotFound("AppUser", "email",email ));

        Medication medication = medicationRepository.findById(medicationId).orElseThrow(()->
                new MedicationNotFoundException("Medication not found with Id : "+medicationId));

        medicationSchedule.setMedication(medication);
        medicationSchedule.setUser(user);
        medicationSchedule.setReminderEnabled(true);
        medicationSchedule.setReminderTime(schedule.getReminderTime());
        medicationSchedule.setDurationInDays(schedule.getDurationInDays());
        medicationSchedule.setTimesPerDay(schedule.getTimesPerDay());

        return convertToDTO(scheduleRepository.save(medicationSchedule));
    }

    public Optional<MedicationSchedule> getMedicationScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    public List<MedicationSchedule> getMedicationSchedulesByUserEmail(String userEmail) {
        return scheduleRepository.findByUserEmail(userEmail);
    }
//    public List<MedicationSchedule> getMedicationSchedulesByUserEmail(String userEmail) {
//        AppUser user = appUserRepository.findById(userEmail)
//                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));
//        return scheduleRepository.findByUser(user);
//    }

    public MedicationSchedule updateMedicationSchedule(Long id, MedicationSchedule updatedSchedule) {
        return scheduleRepository.findById(id)
                .map(schedule -> {
                    schedule.setReminderTime(updatedSchedule.getReminderTime());
                    schedule.setDurationInDays(updatedSchedule.getDurationInDays());
                    schedule.setReminderTime(updatedSchedule.getReminderTime());
                    schedule.setReminderEnabled(updatedSchedule.isReminderEnabled());
                    // Other fields can be updated as needed
                    return scheduleRepository.save(schedule);
                })
                .orElseThrow(() -> new RuntimeException("Medication Schedule not found with id " + id));
    }

    public void deleteMedicationSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
    public List<MedicationSchedule> findAllDueSchedules(LocalTime currentTime) {
        // This method should return all schedules that are due for a reminder at the current time.
        // The logic will depend on how you define a schedule as being "due" based on the reminderTime and reminderFrequency.
        // For simplicity, let's assume you return all schedules with reminderTime equal to the current time and reminderEnabled is true.
        return scheduleRepository.findAll().stream()
                .filter(schedule -> schedule.isReminderEnabled() && schedule.getReminderTime().equals(currentTime))
                .collect(Collectors.toList());
    }

    public void checkAndSendReminders() {
        List<MedicationSchedule> schedules = scheduleRepository.findAll();

        for (MedicationSchedule schedule : schedules) {
            if (schedule.isReminderEnabled() && isTimeForReminder(schedule)) {
                String message = "Reminder: It's time to take your medication - " + schedule.getMedication().getName();
                notificationService.sendNotification(schedule.getUser().getEmail(), message);
            }
        }
    }

    // Method to check if it's time for the reminder
    private boolean isTimeForReminder(MedicationSchedule schedule) {
        String currentTime= dateTimeUtils.Convert();
        return currentTime.equals(schedule.getReminderTime());
    }

    private MedicationScheduleDTO convertToDTO(MedicationSchedule schedule) {

        MedicationScheduleDTO scheduleDTO = new MedicationScheduleDTO();

        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setTimesPerDay(schedule.getTimesPerDay());
        scheduleDTO.setReminderTime(schedule.getReminderTime());
        scheduleDTO.setDurationInDays(schedule.getDurationInDays());
        scheduleDTO.setReminderEnabled(schedule.isReminderEnabled());
        scheduleDTO.setMedicationId(schedule.getMedication().getId());
        scheduleDTO.setUserEmail(schedule.getUser().getEmail());
        return scheduleDTO;
    }

    private MedicationSchedule convertToEntity(MedicationScheduleDTO scheduleDTO) {
        MedicationSchedule schedule = new MedicationSchedule();
        schedule.setId(scheduleDTO.getId());
        schedule.setReminderTime(scheduleDTO.getReminderTime());
        schedule.setTimesPerDay(scheduleDTO.getTimesPerDay());
        schedule.setDurationInDays(scheduleDTO.getDurationInDays());
        schedule.setReminderEnabled(scheduleDTO.isReminderEnabled());
        return schedule;
    }

    // Additional methods can be added as needed
}
