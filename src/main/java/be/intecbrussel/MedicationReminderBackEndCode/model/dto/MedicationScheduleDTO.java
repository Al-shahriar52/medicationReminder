package be.intecbrussel.MedicationReminderBackEndCode.model.dto;

import java.time.LocalTime;

public class MedicationScheduleDTO {
    private Long id;
    private Long medicationId;
    private String userEmail;
    private String reminderTime;
    private int durationInDays;
    private int timesPerDay;
    private boolean reminderEnabled;

    public MedicationScheduleDTO(Long id, Long medicationId, String userEmail, String reminderTime, int durationInDays, int timesPerDay, boolean reminderEnabled) {
        this.id = id;
        this.medicationId = medicationId;
        this.userEmail = userEmail;
        this.reminderTime = reminderTime;
        this.durationInDays = durationInDays;
        this.timesPerDay = timesPerDay;
        this.reminderEnabled = reminderEnabled;
    }

    public MedicationScheduleDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getReminderTime() {
        return reminderTime;
    }

    public void setReminderTime(String reminderTime) {
        this.reminderTime = reminderTime;
    }

    public int getDurationInDays() {
        return durationInDays;
    }

    public void setDurationInDays(int durationInDays) {
        this.durationInDays = durationInDays;
    }

    public int getTimesPerDay() {
        return timesPerDay;
    }

    public void setTimesPerDay(int timesPerDay) {
        this.timesPerDay = timesPerDay;
    }

    public boolean isReminderEnabled() {
        return reminderEnabled;
    }

    public void setReminderEnabled(boolean reminderEnabled) {
        this.reminderEnabled = reminderEnabled;
    }
}
