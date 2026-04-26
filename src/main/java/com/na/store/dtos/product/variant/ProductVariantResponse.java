package com.na.store.dtos.product.variant;

import com.na.store.enums.ClotheSize;

import java.util.Set;

public record ProductVariantResponse(

        String color,
        int stock,
        Set<ClotheSize> sizes
) {}