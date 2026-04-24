package com.na.store.services;

import com.na.store.dtos.reftoken.RefreshTokenResponse;
import com.na.store.entities.RefreshToken;
import com.na.store.entities.User;
import com.na.store.exceptions.ExpiredException;
import com.na.store.exceptions.InvalidException;
import com.na.store.repositories.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${application.security.jwt.refresh-expiration}")
    private long jwtRefreshExpiration;

    public RefreshToken createRefreshToken(User user) {
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .expirationDate(LocalDateTime.now().plusNanos(jwtRefreshExpiration))
                .user(user)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyAndRefreshToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidException("Invalid refresh token"));

        if (refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new ExpiredException("Refresh token expired");
        }

        User user = refreshToken.getUser();
        refreshTokenRepository.delete(refreshToken);

        return createRefreshToken(user);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .ifPresent(rt -> refreshTokenRepository.deleteByUser(rt.getUser()));
    }
}