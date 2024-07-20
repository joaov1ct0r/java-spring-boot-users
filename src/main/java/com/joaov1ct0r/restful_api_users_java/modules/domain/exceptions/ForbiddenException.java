package com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String message) {
        super(message);
    }
}
