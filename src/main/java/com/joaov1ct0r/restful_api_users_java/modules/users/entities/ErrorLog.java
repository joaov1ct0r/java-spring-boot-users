package com.joaov1ct0r.restful_api_users_java.modules.users.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "error_logs")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorLog {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = true)
    @Nullable
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "userId", insertable = false, updatable = false)
    private User user;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Number code;

    @Column(nullable = false)
    private String description;
}