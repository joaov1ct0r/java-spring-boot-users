package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

public class DeleteUserDTO {
    private String userId;

    public DeleteUserDTO() {}

    public DeleteUserDTO(
            String userId
    ) {
        this.userId = userId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
