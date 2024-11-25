package com.joaov1ct0r.restful_api_users_java.modules.users.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserDTO {
    public UUID id;
    public String username;
    public String name;
    public String email;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public UUID userWhoUpdatedId;
    public String photoUrl;

    public UUID getUserWhoUpdatedId() {
        return userWhoUpdatedId;
    }

    public void setUserWhoUpdatedId(UUID userWhoUpdatedId) {
        this.userWhoUpdatedId = userWhoUpdatedId;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhotoUrl() {
        return this.photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}