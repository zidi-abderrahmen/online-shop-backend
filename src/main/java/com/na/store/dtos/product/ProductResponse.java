package com.na.store.dtos.product;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String imageUrl,
        String name,
        String description,
        BigDecimal price
) {}