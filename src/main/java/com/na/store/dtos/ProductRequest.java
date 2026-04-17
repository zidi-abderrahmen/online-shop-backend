package com.na.store.dtos;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record ProductRequest(

        @NotBlank(message = "Image URL cannot be blank")
        String imageUrl,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotBlank(message = "Price cannot be blank")
        BigDecimal price
) {}