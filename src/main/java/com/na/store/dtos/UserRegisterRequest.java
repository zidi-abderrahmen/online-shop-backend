package com.na.store.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRegisterRequest(

        @NotBlank(message = "Name cannot be blank")
        String fullName,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Email must be valid")
        String email,

        @NotBlank(message = "Password cannot be blank")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotBlank(message = "Confirm password cannot be blank")
        @Size(min = 8, message = "Confirm Password must be at least 8 characters long")
        String confirmPassword
) {}