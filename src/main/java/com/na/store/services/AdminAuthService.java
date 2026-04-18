package com.na.store.services;

import com.na.store.dtos.user.UserLoginRequest;
import com.na.store.dtos.user.UserLoginResponse;
import com.na.store.dtos.user.UserRegisterRequest;
import com.na.store.dtos.user.UserResponse;
import com.na.store.entities.User;
import com.na.store.enums.UserRole;
import com.na.store.exceptions.AlreadyExistsException;
import com.na.store.exceptions.InvalidEmailOrPasswordException;
import com.na.store.exceptions.PasswordMismatchException;
import com.na.store.mappers.UserResponseMapper;
import com.na.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminAuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseMapper userResponseMapper;

    @Transactional
    public UserResponse createNewAdmin(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new AlreadyExistsException("Email already exists");
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        User newUser = User.builder()
                .name(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .role(UserRole.ADMIN)
                .build();

        User savedUser = userRepository.save(newUser);

        return userResponseMapper.toDto(savedUser);
    }

}