package com.na.store.dtos.product;

import com.na.store.dtos.product.images.ProductImageResponse;
import com.na.store.dtos.product.variant.ProductVariantResponse;
import com.na.store.enums.ClotheCategory;
import com.na.store.enums.ClotheSize;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public record ProductResponse(
        Long id,
        List<ProductImageResponse> imagesUrl,
        String name,
        String description,
        BigDecimal price,
        ClotheCategory category,
        Set<ProductVariantResponse> variants
) {}