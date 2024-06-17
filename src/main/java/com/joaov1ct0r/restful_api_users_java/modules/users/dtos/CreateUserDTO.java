package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

public class CreateUserDTO {
    private String username;
    private String email;
    private String name;
    private String password;

    public CreateUserDTO() {}

    public CreateUserDTO(
            String username,
            String email,
            String name,
            String password
    ) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
