package com.joaov1ct0r.restful_api_users_java.modules.users.mappers;

import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;

public class UserMapper {
    public static UserDTO toDTO(UserEntity user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setUpdatedAt(user.getUpdatedAt());
        userDTO.setUserWhoUpdatedId(user.getUserWhoUpdatedId());
        return userDTO;
    }
}
