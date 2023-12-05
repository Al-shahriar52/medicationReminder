package be.intecbrussel.MedicationReminderBackEndCode.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFound.class)
    protected ResponseEntity<?> handleResourceNotFoundException(ResourceNotFound ex) {

        StringBuilder errorMessage = new StringBuilder();
        errorMessage.append(ex.getMessage())
                .append(".");

        return ResponseEntity.badRequest().body(errorMessage);

    }
}
