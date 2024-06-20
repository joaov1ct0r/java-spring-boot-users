package com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException (String message) {
        super(message);
    }
}
