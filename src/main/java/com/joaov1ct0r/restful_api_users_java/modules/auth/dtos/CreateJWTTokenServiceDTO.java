package com.joaov1ct0r.restful_api_users_java.modules.auth.dtos;

public class CreateJWTTokenServiceDTO {
    private String token;
    private CreateJWTTokenServicePayloadDTO payload;

    public CreateJWTTokenServiceDTO () {}

    public CreateJWTTokenServiceDTO(
            String token,
            CreateJWTTokenServicePayloadDTO payload
    ) {
        this.token = token;
        this.payload = payload;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CreateJWTTokenServicePayloadDTO getPayload() {
        return this.payload;
    }

    public void setPayload(
            CreateJWTTokenServicePayloadDTO payload
    ) {
        this.token = token;
    }
}
