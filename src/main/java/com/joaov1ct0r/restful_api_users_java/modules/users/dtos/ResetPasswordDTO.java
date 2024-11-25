package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

public class ResetPasswordDTO {
    private String email;

    public ResetPasswordDTO(String email) {
        this.email = email;
    }

    public ResetPasswordDTO() {}

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
