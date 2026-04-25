package com.na.store.dtos.product;

import com.na.store.dtos.product.images.ProductImagesRequest;
import com.na.store.enums.ClotheSize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record ProductRequest(

        @NotEmpty(message = "Image URLs cannot be blank")
        List<ProductImagesRequest> imagesUrl,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message = "Description cannot be blank")
        String description,

        @NotNull(message = "Price cannot be blank")
        @Positive(message = "Price must be positive")
        BigDecimal price,

        @NotNull(message = "Stock cannot be blank")
        @Positive(message = "Stock must be positive")
        int stock,

        @NotNull(message = "Size cannot be blank")
        ClotheSize size
) {}