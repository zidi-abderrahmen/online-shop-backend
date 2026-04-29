package com.na.store.controllers;

import com.na.store.dtos.cart.ItemCartRequest;
import com.na.store.dtos.cart.ItemCartResponse;
import com.na.store.services.ItemCartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class ItemCartController {

    private final ItemCartService itemCartService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ItemCartResponse>> getCart(Authentication authentication) {
        return ResponseEntity.ok(itemCartService.getCart(authentication.getName()));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ItemCartResponse> saveInCart(@Valid @RequestBody ItemCartRequest request, Authentication authentication) {
        return ResponseEntity.ok(itemCartService.saveInCart(request, authentication.getName()));
    }

    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteFromCart(@PathVariable Long id, Authentication authentication) {
        itemCartService.deleteFromCart(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}