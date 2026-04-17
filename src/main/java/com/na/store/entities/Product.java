package com.na.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "products")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Image URL cannot be blank")
    @Column(nullable = false, length = 500)
    private String imageUrl;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Column(nullable = false, length = 500)
    private String description;

    @NotBlank(message = "Price cannot be blank")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}