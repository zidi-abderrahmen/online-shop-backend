package com.na.store.dtos.user;

public record UserLoginResponse(

        String token,
        UserResponse user
) {}