package com.na.store.dtos.product.variant;

import com.na.store.enums.ClotheSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record ProductVariantRequest(

        @NotBlank(message = "Color cannot be blank")
        String color,

        @NotNull(message = "Stock cannot be blank")
        int stock,

        @NotEmpty(message = "Sizes cannot be empty")
        Set<ClotheSize> sizes
) {}