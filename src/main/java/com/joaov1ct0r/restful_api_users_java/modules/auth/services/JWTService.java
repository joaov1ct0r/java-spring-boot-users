package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
    @Value("${security.token.secret}")
    private String secret;

    public DecodedJWT decodeToken(String token) {
        Algorithm algo = Algorithm.HMAC256(this.secret);

        try {
            return JWT
                    .require(algo)
                    .build()
                    .verify(token);
        } catch(JWTVerificationException e) {
            throw new BadRequestException("JWT Verification Exception");
        } catch (Exception e) {
//            throw new RuntimeException("Internal Server Error");
            throw new RuntimeException(e.getMessage());
        }
    }
}
