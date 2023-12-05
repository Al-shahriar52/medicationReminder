package be.intecbrussel.MedicationReminderBackEndCode.controller;

import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.LoginRequest;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.LoginResponse;
import be.intecbrussel.MedicationReminderBackEndCode.service.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class UserController {

    private final RegisterService registerService;

    public UserController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse loginResponse = registerService.login(loginRequest);
            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody LoginRequest loginRequest) {
        registerService.createUser(loginRequest.getEmail(), loginRequest.getPassword());
        return ResponseEntity.ok().build();
    }
    @GetMapping("/getUserByEmail")
    public ResponseEntity getUserByEmail(@RequestParam String email) {
        //replace ByEmail to ByToken once Spring Security is implemented
        //getting registered user by email
        Optional<AppUser> userFromDb = registerService.getUserByEmail(email);

        //if user isn't registered
        if (userFromDb.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(userFromDb.get());
    }

    //UPDATE
    @PatchMapping("/patchUser")
    public ResponseEntity patchUser(@RequestBody AppUser user) {
        try {
            return ResponseEntity.ok(registerService.patchUser(user));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}