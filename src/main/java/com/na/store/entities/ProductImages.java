package com.na.store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity @Table(name = "product-images")
@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class ProductImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @NotBlank(message = "Image URL cannot be blank")
    @Column(nullable = false, length = 500)
    private String url;

    @NotBlank(message = "Alt text cannot be blank")
    @Column(nullable = false, length = 500)
    private String altText;
}