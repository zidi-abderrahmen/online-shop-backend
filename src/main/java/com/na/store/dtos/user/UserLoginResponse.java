package com.na.store.dtos.user;

public record UserLoginResponse(

        String accessToken,
        String refreshToken,
        UserResponse user
) {}