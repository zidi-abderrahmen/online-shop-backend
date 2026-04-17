package com.na.store.mappers;

import com.na.store.dtos.product.ProductResponse;
import com.na.store.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    ProductResponse toDto(Product product);

    Product toEntity(ProductResponse productResponse);
}