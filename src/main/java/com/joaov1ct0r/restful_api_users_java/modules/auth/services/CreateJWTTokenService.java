package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.CreateJWTTokenServicePayloadDTO;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.CreateJWTTokenServiceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.TimeZone;
import java.util.Calendar;

@Service
public class CreateJWTTokenService {
    @Value("${security.token.secret}")
    private String tokenSecret;
    private final String tokenIssuer = "https://api.crud.shop";

    public CreateJWTTokenServiceDTO execute(
            String userId
    ) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.add(Calendar.DAY_OF_YEAR, 2); // Configura a expiração para 2 dia a partir de agora
        Date expirationDate = calendar.getTime();

        Algorithm algorithm = Algorithm.HMAC256(this.tokenSecret);
        var payload = new CreateJWTTokenServicePayloadDTO(userId);

        var token = JWT.create()
                .withIssuer(this.tokenIssuer)
                .withExpiresAt(expirationDate)
                .withSubject(payload.getUserId())
                .withClaim("userId", payload.getUserId())
                .sign(algorithm);

        return new CreateJWTTokenServiceDTO(token, payload);
    }
}
