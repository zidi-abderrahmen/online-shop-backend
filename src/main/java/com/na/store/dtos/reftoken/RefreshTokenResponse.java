package com.na.store.dtos.reftoken;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {}