package com.na.store.services;

import com.na.store.dtos.cart.ItemCartRequest;
import com.na.store.dtos.cart.ItemCartResponse;
import com.na.store.entities.ItemCart;
import com.na.store.entities.Product;
import com.na.store.entities.User;
import com.na.store.exceptions.InvalidException;
import com.na.store.exceptions.NotFoundException;
import com.na.store.mappers.ItemCartMapper;
import com.na.store.repositories.ItemCartRepository;
import com.na.store.repositories.ProductRepository;
import com.na.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemCartService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ItemCartRepository itemCartRepository;
    private final ItemCartMapper itemCartMapper;

    public List<ItemCartResponse> getCart(String userId) {
        return itemCartRepository.findByUserId(userId).stream().map(itemCartMapper::toDto).toList();
    }

    public ItemCartResponse saveInCart(ItemCartRequest request, String userEmail) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new NotFoundException("User not found"));

        ItemCart itemCart = itemCartRepository.findCartItem(userEmail, request.productId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + request.quantity());
                    return existing;
                })
                .orElseGet(() -> ItemCart.builder()
                        .quantity(request.quantity())
                        .user(user)
                        .product(product)
                        .build());

        return itemCartMapper.toDto(itemCartRepository.save(itemCart));
    }

    public void deleteFromCart(Long id, String userEmail) {
        ItemCart item = itemCartRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Item not found"));

        if (!item.getUser().getEmail().equals(userEmail)) {
            throw new InvalidException("You can only remove items from your own cart");
        }

        itemCartRepository.delete(item);
    }
}