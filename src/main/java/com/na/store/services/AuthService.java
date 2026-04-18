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
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserResponseMapper userResponseMapper;
    private final JwtService jwtService;

    @Transactional
    public UserResponse createNewUser(UserRegisterRequest request) {
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
                .role(UserRole.USER)
                .build();

        User savedUser = userRepository.save(newUser);

        return userResponseMapper.toDto(savedUser);
    }

    public UserLoginResponse loginUser(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new InvalidEmailOrPasswordException("Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidEmailOrPasswordException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);
        return new UserLoginResponse(token, userResponseMapper.toDto(user));
    }
}