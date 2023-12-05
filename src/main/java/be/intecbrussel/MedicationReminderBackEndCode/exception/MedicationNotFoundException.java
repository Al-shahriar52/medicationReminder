package be.intecbrussel.MedicationReminderBackEndCode.exception;

public class MedicationNotFoundException extends Throwable{
    public MedicationNotFoundException(String message){
        super(message);
    }
}