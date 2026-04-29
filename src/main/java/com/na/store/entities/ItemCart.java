package com.na.store.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "item_cart")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ItemCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private int quantity;

}