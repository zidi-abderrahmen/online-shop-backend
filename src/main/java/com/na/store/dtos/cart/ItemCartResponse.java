package com.na.store.dtos.cart;

import com.na.store.dtos.product.ProductResponse;

public record ItemCartResponse(

        Long cartItemId,
        ProductResponse product,
        int quantity
) {}