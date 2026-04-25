package com.na.store.dtos.product.images;

import jakarta.validation.constraints.NotBlank;

public record ProductImagesRequest(

    @NotBlank(message = "Image URL cannot be blank")
    String url,

    @NotBlank(message = "Alt text cannot be blank")
    String altText
) {}