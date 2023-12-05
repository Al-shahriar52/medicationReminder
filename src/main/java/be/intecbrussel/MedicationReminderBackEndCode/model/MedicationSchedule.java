package be.intecbrussel.MedicationReminderBackEndCode.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
    public class MedicationSchedule {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        private Medication medication;

        @ManyToOne
        private AppUser user;

    private String reminderTime;
    private int durationInDays;
    private int timesPerDay;

    private boolean reminderEnabled;

    public MedicationSchedule() {
    }

    public MedicationSchedule(Long id, Medication medication, AppUser user, String reminderTime, int durationInDays, int timesPerDay, boolean reminderEnabled) {
        this.id = id;
        this.medication = medication;
        this.user = user;
        this.reminderTime = reminderTime;
        this.durationInDays = durationInDays;
        this.timesPerDay = timesPerDay;
        this.reminderEnabled = reminderEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
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
