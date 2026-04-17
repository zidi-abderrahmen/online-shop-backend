package com.na.store.dtos;

import java.math.BigDecimal;

public record ProductResponse(
        String name,
        String description,
        BigDecimal price
) {}