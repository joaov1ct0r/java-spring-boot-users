package com.joaov1ct0r.restful_api_users_java.modules.posts.dtos;

import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class PostDTO {
    public UUID id;
    public String content;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public UserEntity userWhoCreatedId;
    public UserEntity userWhoUpdatedId;

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserEntity getUserWhoCreatedId() {
        return this.userWhoCreatedId;
    }

    public void setUserWhoCreatedId(UserEntity userWhoCreatedId) {
        this.userWhoCreatedId = userWhoCreatedId;
    }

    public UserEntity getUserWhoUpdatedId() {
        return this.userWhoUpdatedId;
    }

    public void setUserWhoUpdatedId(UserEntity userWhoUpdatedId) {
        this.userWhoUpdatedId = userWhoUpdatedId;
    }
}
