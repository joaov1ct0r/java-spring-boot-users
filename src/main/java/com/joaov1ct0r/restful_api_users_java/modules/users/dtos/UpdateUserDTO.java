package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

import jakarta.annotation.Nullable;

public class UpdateUserDTO {
    private String id;
    @Nullable
    private String username;
    @Nullable
    private String email;
    @Nullable
    private String name;
    @Nullable
    private String password;

    public UpdateUserDTO() {}

    public UpdateUserDTO(
            String id,
            @Nullable String username,
            @Nullable String email,
            @Nullable String name,
            @Nullable String password
    ) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Nullable
    public String getUsername() {
        return this.username;
    }

    public void setUsername(@Nullable String username) {
        this.username = username;
    }

    @Nullable
    public String getEmail() {
        return this.email;
    }

    public void setEmail(@Nullable String email) {
        this.email = email;
    }

    @Nullable
    public String getName() {
        return this.name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    @Nullable
    public String getPassword() {
        return this.password;
    }

    public void setPassword(@Nullable String password) {
        this.password = password;
    }
}
