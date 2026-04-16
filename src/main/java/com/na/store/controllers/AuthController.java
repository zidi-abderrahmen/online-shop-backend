package com.na.store.controllers;

import com.na.store.dtos.UserLoginRequest;
import com.na.store.dtos.UserRegisterRequest;
import com.na.store.dtos.UserResponse;
import com.na.store.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody UserRegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.createNewUser(request));
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@Valid @RequestBody UserLoginRequest request) {
        return ResponseEntity.ok(authService.loginUser(request));
    }
}