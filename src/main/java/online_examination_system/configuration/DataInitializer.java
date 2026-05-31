package online_examination_system.configuration;

import lombok.RequiredArgsConstructor;
import online_examination_system.model.entity.User;
import online_examination_system.model.enums.Role;
import online_examination_system.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "adminpatris@gmail.com";
        if (userRepository.findByEmail(adminEmail).isEmpty()) {

            User admin = new User();
            admin.setName("Patris");
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("patris777"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}
