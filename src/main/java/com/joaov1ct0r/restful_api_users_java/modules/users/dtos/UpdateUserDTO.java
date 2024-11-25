package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

import jakarta.annotation.Nullable;

public class UpdateUserDTO {
    @Nullable
    private String username;
    @Nullable
    private String email;
    @Nullable
    private String name;
    @Nullable
    private String password;
    @Nullable
    private String photoUrl;

    public UpdateUserDTO() {}

    public UpdateUserDTO(
            @Nullable String username,
            @Nullable String email,
            @Nullable String name,
            @Nullable String password,
            @Nullable String photoUrl
    ) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;
        this.photoUrl = photoUrl;
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

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
