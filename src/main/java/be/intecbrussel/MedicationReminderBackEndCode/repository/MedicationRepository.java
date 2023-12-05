package be.intecbrussel.MedicationReminderBackEndCode.repository;

import be.intecbrussel.MedicationReminderBackEndCode.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Long> {
    List<Medication> findAllByUserEmail(String user_email);

    // New method to find medications that are due for a reminder
//    List<Medication> findByReminderEnabledTrueAndNextReminderTimeBefore(Date currentTime);
}
