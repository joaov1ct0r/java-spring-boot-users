package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import org.springframework.boot.web.server.Cookie;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class CreateCookieService {
    public Cookie execute(String cookieName, String domain) {
        var cookie = new Cookie();
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        cookie.setName(cookieName);
        cookie.setPath("/");
        cookie.setSecure(true);
        cookie.setSameSite(Cookie.SameSite.STRICT);
        cookie.setMaxAge(Duration.ofHours(1));

        return cookie;
        // add response.addCookie(cookie) to the controller
        // to filter the incoming requests and get the cookies watch: https://www.youtube.com/watch?v=-_67Cuu_5T8
    }
}
