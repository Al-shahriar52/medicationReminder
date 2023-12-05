package be.intecbrussel.MedicationReminderBackEndCode.security;
import be.intecbrussel.MedicationReminderBackEndCode.model.AppUser;
import be.intecbrussel.MedicationReminderBackEndCode.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<AppUser> oUser = userRepository.findById(email);
        if (oUser.isEmpty()) {
            throw new UsernameNotFoundException(email + " not found.");
        }

        AppUser user = oUser.get();

        UserDetails userDetails = User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();

        return userDetails;
    }
}
