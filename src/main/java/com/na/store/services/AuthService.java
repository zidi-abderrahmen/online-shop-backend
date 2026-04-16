package com.na.store.services;

import com.na.store.dtos.UserLoginRequest;
import com.na.store.dtos.UserRegisterRequest;
import com.na.store.dtos.UserResponse;
import com.na.store.entities.User;
import com.na.store.exceptions.EmailAlreadyExistsException;
import com.na.store.exceptions.EmailNotFoundException;
import com.na.store.exceptions.InvalidEmailOrPasswordException;
import com.na.store.exceptions.PasswordMismatchException;
import com.na.store.mappers.UserResponseMapper;
import com.na.store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseMapper userResponseMapper;

    @Transactional
    public UserResponse createNewUser(UserRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException("Email already exists");
        }

        if (!request.password().equals(request.confirmPassword())) {
            throw new PasswordMismatchException("Passwords do not match");
        }

        User newUser = User.builder()
                .name(request.fullName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .build();

        User savedUser = userRepository.save(newUser);

        return userResponseMapper.toDto(savedUser);
    }

    public UserResponse loginUser(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new EmailNotFoundException("Email not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidEmailOrPasswordException("Invalid email or password");
        }

        return userResponseMapper.toDto(user);
    }
}