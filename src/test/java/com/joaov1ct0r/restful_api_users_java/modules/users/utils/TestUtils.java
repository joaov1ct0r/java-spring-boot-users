package com.joaov1ct0r.restful_api_users_java.modules.users.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;

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
}
