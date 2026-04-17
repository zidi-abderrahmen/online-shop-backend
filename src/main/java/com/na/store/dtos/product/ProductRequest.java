package com.na.store.dtos.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Image URL cannot be blank")
        String imageUrl,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Price cannot be blank")
        @Positive(message = "Price must be positive")
        BigDecimal price
) {}