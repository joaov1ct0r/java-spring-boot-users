package com.joaov1ct0r.restful_api_users_java.modules.domain.mappers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {
    public ResponseDTO toDTO(
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
