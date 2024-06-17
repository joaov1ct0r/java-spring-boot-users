package com.joaov1ct0r.restful_api_users_java.modules.domain.dtos;

import jakarta.annotation.Nullable;

public class ResponseDTO {
    public Number statusCode;
    public String message;
    @Nullable
    public Object resource;

    public Number getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Number statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Nullable
    public Object getResource() {
        return resource;
    }

    public void setResource(@Nullable Object resource) {
        this.resource = resource;
    }

    public ResponseDTO() {}

    public ResponseDTO(
            Number statusCode,
            String message,
            @Nullable Object resource
    ) {
        this.statusCode = statusCode;
        this.message = message;
        this.resource = resource;
    }
}
