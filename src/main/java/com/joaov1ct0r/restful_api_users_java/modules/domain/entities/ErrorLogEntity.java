package com.joaov1ct0r.restful_api_users_java.modules.domain.entities;

import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "error_logs")
public class ErrorLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = true)
    @Nullable
    private UUID userId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private UserEntity user;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    private String description;

    public ErrorLogEntity() {}

    public ErrorLogEntity(
            UUID id,
            @Nullable UUID userId,
            LocalDateTime timestamp,
            Integer code,
            String description
    ) {
        this.id = id;
        this.userId = userId;
        this.timestamp = timestamp;
        this.code = code;
        this.description = description;
    }
}