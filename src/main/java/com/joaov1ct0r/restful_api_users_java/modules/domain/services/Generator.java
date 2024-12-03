package com.joaov1ct0r.restful_api_users_java.modules.domain.services;

import org.springframework.stereotype.Service;
import java.util.Random;

@Service
public class Generator {
    private final String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%&";
    private final Random rnd = new Random();
    public String generateRandomPassword(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(this.chars.charAt(this.rnd.nextInt(this.chars.length())));
        }

        return sb.toString();
    }
}
