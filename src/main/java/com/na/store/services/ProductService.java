package com.na.store.services;

import com.na.store.dtos.ProductRequest;
import com.na.store.dtos.ProductResponse;
import com.na.store.entities.Product;
import com.na.store.exceptions.AlreadyExistsException;
import com.na.store.exceptions.NotFoundException;
import com.na.store.mappers.ProductResponseMapper;
import com.na.store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    @Transactional
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productResponseMapper::toDto).toList();
    }

    public ProductResponse getProductById(Long id) {
        return productResponseMapper.toDto(productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found")));
    }

    @Transactional
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
