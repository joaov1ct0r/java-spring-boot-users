package com.joaov1ct0r.restful_api_users_java.modules.posts.entities;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.EventLogEntity;
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWhoCreatedId", insertable = false, updatable = false)
    private UUID userWhoCreatedId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWhoUpdatedId", insertable = false, updatable = false)
    private UUID userWhoUpdatedId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
    private List<ErrorLogEntity> errorLogs;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "post")
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
}
