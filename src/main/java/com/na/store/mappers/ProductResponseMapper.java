package com.na.store.mappers;

import com.na.store.dtos.product.ProductResponse;
import com.na.store.dtos.product.images.ProductImageResponse;
import com.na.store.entities.Product;
import com.na.store.entities.ProductImages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductResponseMapper {

    @Mapping(target = "imagesUrl", source = "imagesUrl")
    ProductResponse toDto(Product product);

    ProductImageResponse toDto(ProductImages productImages);

    Product toEntity(ProductResponse productResponse);
}