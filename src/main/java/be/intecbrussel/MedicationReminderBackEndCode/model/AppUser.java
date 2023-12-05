package be.intecbrussel.MedicationReminderBackEndCode.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AppUser {
    @Id
    private String email;
    private String password;
    public AppUser(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected AppUser(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
