package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.CreateJWTTokenServicePayloadDTO;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.CreateJWTTokenServiceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.Instant;

@Service
public class CreateJWTTokenService {
    @Value("${security.token.secret}")
    private String tokenSecret;

    private final Instant tokenExpiration = Instant.now().plus(Duration.ofMinutes(10));

    public CreateJWTTokenServiceDTO execute(
            String userId
    ) {
        Algorithm algorithm = Algorithm.HMAC256(this.tokenSecret);
        var payload = new CreateJWTTokenServicePayloadDTO(userId);
        String tokenIssuer = "localhost";

        var token = JWT.create()
                .withIssuer(tokenIssuer)
                .withExpiresAt(this.tokenExpiration)
                .withSubject(payload.getUserId())
                .withClaim("userId", payload.getUserId())
                .sign(algorithm);

        return new CreateJWTTokenServiceDTO(token, payload);
    }
}
