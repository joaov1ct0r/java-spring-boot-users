package com.joaov1ct0r.restful_api_users_java.modules.posts.entities;

import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity(name = "posts")
public class PostEntity {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Content must not be blank")
    private String content;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column()
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column()
    private UUID userWhoCreatedId;

    @ManyToOne
    @JoinColumn(name = "UserWhoCreatedId", insertable = false, updatable = false)
    private UserEntity userWhoCreated;

    public PostEntity() {}

    public PostEntity(
            UUID id,
            String content,
            LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt,
            UUID userWhoCreatedId
    ) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userWhoCreatedId = userWhoCreatedId;
    }

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

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UUID getUserWhoCreatedId() {
        return this.userWhoCreatedId;
    }
}
