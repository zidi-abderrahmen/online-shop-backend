package com.na.store.dtos.product;

import com.na.store.dtos.product.images.ProductImagesRequest;
import com.na.store.dtos.product.variant.ProductVariantRequest;
import com.na.store.enums.ClotheCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ProductRequest(

        @NotEmpty(message = "Image URLs cannot be empty")
        @Valid
        List<ProductImagesRequest> imagesUrl,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Price cannot be null")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Category cannot be blank")
        ClotheCategory category,

        @NotEmpty(message = "Product variant cannot be empty")
        @Valid
        Set<ProductVariantRequest> variants
) {}