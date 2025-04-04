package com.joaov1ct0r.restful_api_users_java.modules.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.mappers.PostMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import org.springframework.mock.web.MockMultipartFile;
import java.time.LocalDateTime;
import java.util.UUID;

public class TestUtils {
    public static MockMultipartFile stringToMMF(UserEntity user) {
        String jsonData = String.format("{\"name\":\"%s\", \"email\":\"%s\", \"username\": \"%s\", \"password\": \"%s\", \"id\": \"%s\", \"photoUrl\": \"%s\", \"createdAt\": \"%s\", \"updatedAt\": \"%s\"}", user.getName(), user.getEmail(), user.getUsername(), user.getPassword(), user.getId(), user.getPhotoUrl(), user.getCreatedAt(), user.getUpdatedAt());
        return new MockMultipartFile("user", "", "application/json", jsonData.getBytes());
    }

    public static String objectToJson(Object obj) {
        try {
            final ObjectMapper objMapper = new ObjectMapper();
            objMapper.registerModule(new JavaTimeModule());
            return objMapper.writeValueAsString(obj);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static ResponseDTO jsonToObject(String json, Class<ResponseDTO> responseDTO) {
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(json, responseDTO);
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static PostDTO jsonToPostDTO(String json, Class<PostDTO> postDTO) {
        try {
            String data = json.substring(json.indexOf("{") + 1, json.lastIndexOf("}"));

            String[] parts = data.split(",\\s*");

            String id = parts[0].split("=")[1];
            String content = parts[1].split("=")[1];
            String createdAt = parts[2].split("=")[1];
            String updatedAt = parts[3].split("=")[1];
            String userWhoCreatedId = parts[4].split("=")[1];

            return PostMapper.toDTO(
                    new PostEntity(
                            UUID.fromString(id),
                            content,
                            LocalDateTime.parse(createdAt),
                            LocalDateTime.parse(updatedAt),
                            UUID.fromString(userWhoCreatedId)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static UserDTO jsonToUserDTO(String json, Class<UserDTO> userDTO) {
        try {
            String data = json.substring(json.indexOf("{") + 1, json.lastIndexOf("}"));

            String[] parts = data.split(",\\s*");

            String id = parts[0].split("=")[1];
            String username = parts[1].split("=")[1];
            String name = parts[2].split("=")[1];
            String email = parts[3].split("=")[1];

            return UserMapper.toDTO(
                    new UserEntity(
                            UUID.fromString(id),
                            name,
                            email,
                            username,
                            "any_password",
                            "any_photo_url",
                            LocalDateTime.now(),
                            LocalDateTime.now()
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
