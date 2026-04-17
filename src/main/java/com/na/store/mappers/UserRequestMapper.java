package com.na.store.mappers;

import com.na.store.dtos.user.UserRegisterRequest;
import com.na.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRegisterRequest toDto(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    User toEntity(UserRegisterRequest userRegisterRequest);
}