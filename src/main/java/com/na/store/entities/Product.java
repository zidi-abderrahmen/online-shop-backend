package com.na.store.entities;

import com.na.store.enums.ClotheSize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity @Table(name = "products")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImages> imagesUrl;

    @NotBlank(message = "Name cannot be blank")
    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @Column(nullable = false, length = 500)
    private String description;

    @NotNull(message = "Price cannot be blank")
    @Positive(message = "Price must be positive")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Stock cannot be blank")
    @Positive(message = "Stock must be positive")
    @Column(nullable = false)
    private int stock;

    @NotNull(message = "Size cannot be blank")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ClotheSize size = ClotheSize.M;

    @Version
    private Integer version;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;
}