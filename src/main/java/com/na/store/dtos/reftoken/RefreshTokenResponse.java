package com.na.store.dtos.reftoken;

public record RefreshTokenResponse(
        String token,
        String expiredDate
) {}