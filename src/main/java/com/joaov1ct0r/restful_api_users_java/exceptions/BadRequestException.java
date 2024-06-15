package com.joaov1ct0r.restful_api_users_java.modules.users.exceptions;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
