package com.joaov1ct0r.restful_api_users_java.modules.users.mappers;

import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.ResponseDTO;
import jakarta.annotation.Nullable;

public class ResponseMapper {
    public static ResponseDTO toDTO(
            Number statusCode,
            String message,
            @Nullable Object resource
            ) {

        return new ResponseDTO(
               statusCode,
               message,
               resource
       );
    }
}
