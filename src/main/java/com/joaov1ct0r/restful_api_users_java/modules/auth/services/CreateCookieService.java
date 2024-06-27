package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import jakarta.servlet.http.Cookie;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class CreateCookieService {
    public Cookie execute(String cookieName, String cookieValue, String domain) {
        var cookie = new Cookie(cookieName, cookieValue);
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setMaxAge((int) Duration.ofHours(1).toSeconds());

        return cookie;
    }
}
