package com.joaov1ct0r.restful_api_users_java.modules.auth.dtos;

public class CreateJWTTokenServicePayloadDTO {
    private String userId;

    public CreateJWTTokenServicePayloadDTO() {}

    public CreateJWTTokenServicePayloadDTO(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
