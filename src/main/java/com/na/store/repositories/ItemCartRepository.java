package com.na.store.repositories;

import com.na.store.entities.ItemCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

    @Query("SELECT ic FROM ItemCart ic WHERE ic.user.email = :email")
    List<ItemCart> findByUserEmail(@Param("email") String email);

    @Query("SELECT ic FROM ItemCart ic WHERE ic.user.email = :email AND ic.product.id = :productId")
    Optional<ItemCart> findCartItem(@Param("email") String email, @Param("productId") Long productId);

    void deleteByUserId(String userId);
}