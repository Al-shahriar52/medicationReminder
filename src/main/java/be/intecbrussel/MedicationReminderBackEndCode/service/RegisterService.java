package be.intecbrussel.MedicationReminderBackEndCode.service;

import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.LoginRequest;
import be.intecbrussel.MedicationReminderBackEndCode.model.dto.LoginResponse;
import be.intecbrussel.MedicationReminderBackEndCode.repository.UserRepository;
import be.intecbrussel.MedicationReminderBackEndCode.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public RegisterService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public void createUser(String email, String password) {
        AppUser user = new AppUser(email, bCryptPasswordEncoder.encode(password));
        userRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        String email = authentication.getName();
        AppUser user = new AppUser(email, "");
        String token = jwtUtil.createToken(user);
        LoginResponse loginResponse = new LoginResponse(email, token);

        return loginResponse;
    }
    public Optional<AppUser> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    //UPDATE
    public Optional<AppUser> patchUser(AppUser user) {
        return Optional.of(userRepository.save(user));
    }
}
