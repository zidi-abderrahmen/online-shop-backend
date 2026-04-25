package com.na.store.dtos.product;

import com.na.store.dtos.product.images.ProductImageResponse;
import com.na.store.enums.ClotheSize;

import java.math.BigDecimal;
import java.util.List;

public record ProductResponse(
        Long id,
        List<ProductImageResponse> imagesUrl,
        String name,
        String description,
        BigDecimal price,
        int stock,
        ClotheSize size
) {}