package com.na.store.configs;

import com.na.store.entities.User;
import com.na.store.enums.UserRole;
import com.na.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SuperAdminSeeder {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.super_admin.full_name}")
    private String fullName;

    @Value("${application.super_admin.email}")
    private String email;

    @Value("${application.super_admin.password}")
    private String password;

    @Bean
    public ApplicationRunner createSuperAdmin() {
        return args -> {
            if (!userRepository.existsByEmail(email)) {
                User superAdmin = User.builder()
                        .name(fullName)
                        .email(email)
                        .password(passwordEncoder.encode(password))
                        .role(UserRole.SUPER_ADMIN)
                        .build();

                userRepository.save(superAdmin);
            }
        };
    }
}
