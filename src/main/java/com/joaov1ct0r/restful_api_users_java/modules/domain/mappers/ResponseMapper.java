package com.joaov1ct0r.restful_api_users_java.modules.domain.mappers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponsePagDTO;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {
    public ResponsePagDTO toPagDTO(
            int statusCode,
            String message,
            @Nullable Object resource,
            int prevPage,
            int nextPage,
            int total
    ) {
        return new ResponsePagDTO(
                statusCode,
                message,
                resource,
                prevPage,
                nextPage,
                total
        );
    }

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
