package com.na.store.mappers;

import com.na.store.dtos.cart.ItemCartResponse;
import com.na.store.entities.ItemCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ProductResponseMapper.class})
public interface ItemCartMapper {

    @Mapping(target = "cartItemId", source = "id")
    ItemCartResponse toDto(ItemCart itemCart);

    ItemCart toEntity(ItemCartResponse itemCartResponse);
}