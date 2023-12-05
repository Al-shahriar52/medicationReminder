package be.intecbrussel.MedicationReminderBackEndCode.repository;

import be.intecbrussel.MedicationReminderBackEndCode.model.MedicationSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MedicationScheduleRepository extends JpaRepository<MedicationSchedule, Long> {

    // Example custom query: find schedules by user ID
    List<MedicationSchedule> findByUserEmail(String userEmail);

    // You can add more custom query methods if needed
}
