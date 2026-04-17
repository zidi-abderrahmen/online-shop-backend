package com.na.store.services;

import com.na.store.dtos.ProductRequest;
import com.na.store.dtos.ProductResponse;
import com.na.store.entities.Product;
import com.na.store.exceptions.AlreadyExistsException;
import com.na.store.mappers.ProductResponseMapper;
import com.na.store.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    public ProductResponse saveProduct(ProductRequest request) {
        if (productRepository.existsByNameIgnoreCase(request.name())) {
            throw new AlreadyExistsException("Product already exists");
        }

        Product product = Product.builder()
                .imageUrl(request.imageUrl())
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .build();

        Product savedProduct = productRepository.save(product);

        return productResponseMapper.toDto(savedProduct);
    }
}
