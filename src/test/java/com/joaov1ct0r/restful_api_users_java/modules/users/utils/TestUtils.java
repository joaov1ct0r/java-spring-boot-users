package com.joaov1ct0r.restful_api_users_java.modules.users.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
}
