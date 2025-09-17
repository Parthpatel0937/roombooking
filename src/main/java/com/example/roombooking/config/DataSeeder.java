package com.example.roombooking.config;

import com.example.roombooking.entity.AppUser;
import com.example.roombooking.entity.Role;
import com.example.roombooking.entity.Room;
import com.example.roombooking.repository.RoomRepository;
import com.example.roombooking.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner init(UserRepository userRepository, RoomRepository roomRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                AppUser admin = new AppUser();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(Role.ROLE_ADMIN));
                admin.setEnabled(true);
                userRepository.save(admin);
            }
            if (userRepository.findByUsername("user").isEmpty()) {
                AppUser user = new AppUser();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(Set.of(Role.ROLE_USER));
                user.setEnabled(true);
                userRepository.save(user);
            }
            if (roomRepository.count() == 0) {
                Room r1 = new Room();
                r1.setName("Conference Room A");
                r1.setCapacity(10);
                r1.setType("Conference");
                roomRepository.save(r1);
            }
        };
    }
}
