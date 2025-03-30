package com.joaov1ct0r.restful_api_users_java.modules.posts.entities;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.EventLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
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

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    @NotBlank(message = "User who created id must not be blank")
    private UUID userWhoCreatedId;

    @Column(nullable = true)
    private UUID userWhoUpdatedId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWhoCreatedId", insertable = false, updatable = false, referencedColumnName = "id")
    private UserEntity userWhoCreated;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWhoUpdatedId", insertable = false, updatable = false)
    private UserEntity userWhoUpdated;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    private List<ErrorLogEntity> errorLogs;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "id")
    private List<EventLogEntity> eventLogs;

    public PostEntity() {}

    public PostEntity(
            UUID id,
            String content,
            LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt,
            UUID userWhoCreatedId,
            @Nullable UUID userWhoUpdatedId
    ) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userWhoCreatedId = userWhoCreatedId;
        this.userWhoUpdatedId = userWhoUpdatedId;
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

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public void setUserWhoCreatedId(UUID userWhoCreatedId) {
        this.userWhoCreatedId = userWhoCreatedId;
    }

    public UUID getUserWhoUpdatedId() {
        return this.userWhoUpdatedId;
    }

    public void setUserWhoUpdatedId(UUID userWhoUpdatedId) {
        this.userWhoUpdatedId = userWhoUpdatedId;
    }

    public UserEntity getUserWhoCreated() {
        return this.userWhoCreated;
    }

    public UserEntity getUserWhoUpdated() {
        return this.userWhoUpdated;
    }
}
