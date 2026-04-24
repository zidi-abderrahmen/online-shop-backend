package com.na.store.dtos;

public record RefreshTokenResponse(
        String token,
        String expiredDate
) {}