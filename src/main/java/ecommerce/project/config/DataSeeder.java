package ecommerce.project.config;

import ecommerce.project.dto.user.Role;
import ecommerce.project.entity.User;
import ecommerce.project.respositity.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAdmin(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            String username = "admin";
            String password = "admin";
            String email = "admin@gmail.com";
            if (userRepository.existsByEmail(email)){
                return;
            }
            User user =
                    new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            user.setEmail(email);
            user.setRole(Role.ADMIN);
            userRepository.save(user);

        };
    }
}