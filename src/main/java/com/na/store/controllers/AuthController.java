package com.na.store.controllers;

import com.na.store.dtos.UserRegisterRequest;
import com.na.store.dtos.UserRegisterResponse;
import com.na.store.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserRegisterResponse register(@Valid @RequestBody UserRegisterRequest request) {
        return authService.createNewUser(request);
    }
}