package com.na.store.dtos.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ItemCartRequest(

        @NotNull(message = "Product ID cannot be null")
        Long productId,

        @Positive(message = "Quantity must be positive")
        int quantity
) {}