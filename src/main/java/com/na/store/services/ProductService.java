package com.na.store.services;

import com.na.store.dtos.product.ProductRequest;
import com.na.store.dtos.product.ProductResponse;
import com.na.store.entities.Product;
import com.na.store.exceptions.AlreadyExistsException;
import com.na.store.exceptions.NotFoundException;
import com.na.store.mappers.ProductResponseMapper;
import com.na.store.repositories.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(productResponseMapper::toDto).toList();
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public ProductResponse updateProduct(ProductRequest request, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));

        if (productRepository.existsByNameIgnoreCase(request.name())
                && !product.getName().equalsIgnoreCase(request.name())) {
            throw new AlreadyExistsException("Product with name '" + request.name() + "' already exists");
        }

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setImageUrl(request.imageUrl());

        return productResponseMapper.toDto(product);
    }

    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new NotFoundException("Product not found");
        }

        productRepository.deleteById(id);
    }
}