package com.na.store.entities;

import com.na.store.enums.ClotheSize;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity @Table(name = "product_variant")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    private Set<ClotheSize> sizes;
}