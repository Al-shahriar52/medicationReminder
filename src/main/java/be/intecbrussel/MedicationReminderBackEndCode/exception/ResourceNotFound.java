package be.intecbrussel.MedicationReminderBackEndCode.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResourceNotFound extends RuntimeException{

    private String resourceName;
    private String fieldName;
    private String value;

    public ResourceNotFound(String resourceName, String fieldName, String value) {
        super(String.format("%s is not found with %s : %s",resourceName, fieldName, value ));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.value = value;
    }
}
