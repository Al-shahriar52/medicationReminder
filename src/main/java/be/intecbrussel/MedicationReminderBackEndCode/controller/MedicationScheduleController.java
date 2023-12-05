package be.intecbrussel.MedicationReminderBackEndCode.controller;

import be.intecbrussel.MedicationReminderBackEndCode.exception.MedicationNotFoundException;
import be.intecbrussel.MedicationReminderBackEndCode.model.MedicationSchedule;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.MedicationScheduleDTO;
import be.intecbrussel.MedicationReminderBackEndCode.service.MedicationScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/medication-schedules")
public class MedicationScheduleController {

    private final MedicationScheduleService scheduleService;

    @Autowired
    public MedicationScheduleController(MedicationScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping("/medication/{medicationId}/{email}/addSchedule")
    public ResponseEntity<MedicationScheduleDTO> createSchedule(@RequestBody MedicationScheduleDTO scheduleDTO,
                                                             @PathVariable Long medicationId,
                                                             @PathVariable String email)
                                                    throws MedicationNotFoundException {

        MedicationScheduleDTO createdSchedule = scheduleService.createMedicationSchedule(scheduleDTO, medicationId, email);
        return ResponseEntity.ok(createdSchedule);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicationScheduleDTO> getScheduleById(@PathVariable Long id) {
        MedicationSchedule schedule = scheduleService.getMedicationScheduleById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        return ResponseEntity.ok(convertToDTO(schedule));
    }

    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<MedicationScheduleDTO>> getSchedulesByUserEmail(@PathVariable String userEmail) {
        List<MedicationSchedule> schedules = scheduleService.getMedicationSchedulesByUserEmail(userEmail);
        List<MedicationScheduleDTO> scheduleDTOs = schedules.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(scheduleDTOs);
    }


    @PutMapping("/{id}")
    public ResponseEntity<MedicationSchedule> updateSchedule(@PathVariable Long id, @RequestBody MedicationScheduleDTO scheduleDTO) {
        MedicationSchedule updatedSchedule = convertToEntity(scheduleDTO);
        MedicationSchedule schedule = scheduleService.updateMedicationSchedule(id, updatedSchedule);
        return ResponseEntity.ok(schedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteMedicationSchedule(id);
        return ResponseEntity.ok().build();
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

}
