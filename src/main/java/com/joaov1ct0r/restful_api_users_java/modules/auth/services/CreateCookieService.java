package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CreateCookieService {
    @Value("${SPRING_PROFILES_ACTIVE:prod}")
    private String env;

    public Cookie execute(String cookieName, String cookieValue, String domain) {
        int twoDaysInSeconds = 2 * 24 * 60 * 60; // 2 dias em segundos
        var cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge(twoDaysInSeconds);
        if (this.env.equals("dev")) {
            cookie.setSecure(false);
        }

        return cookie;
    }
}
