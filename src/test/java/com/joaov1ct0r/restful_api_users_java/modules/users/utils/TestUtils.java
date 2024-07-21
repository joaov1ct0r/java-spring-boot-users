package com.joaov1ct0r.restful_api_users_java.modules.users.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;
import java.util.UUID;

public class TestUtils {
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

    public static UserDTO jsonToUserDTO(String json, Class<UserDTO> userDTO) {
        try {
            System.out.println(userDTO);
            String data = json.substring(json.indexOf("{") + 1, json.lastIndexOf("}"));

            // Split by comma and whitespace
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
                            LocalDateTime.now(),
                            LocalDateTime.now(),
                            null
                    )
            );

//            String[] arr = json.split(",");
//
//            for (int index = 0; index < arr.length; index++){
//                if (index == 0) {}
//                if (index == arr.length) {}
//
//                String[] propAndValue = arr[index].split("=");
//                String prop = propAndValue[0];
//                String value = propAndValue[1];
//            }

//            final ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.registerModule(new JavaTimeModule());
//            return objectMapper.readValue(json, userDTO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
