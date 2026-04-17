package com.na.store.dtos;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String imageUrl,
        String name,
        String description,
        BigDecimal price
) {}