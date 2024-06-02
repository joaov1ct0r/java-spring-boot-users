package com.joaov1ct0r.restful_api_users_java.modules.users.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Column(nullable = false)
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço")
    private String username;

    @Email
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(nullable = true)
    @Nullable
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(nullable = true)
    @Nullable
    private UUID userWhoUpdatedId;

    @ManyToOne
    @JoinColumn(name = "userWhoUpdatedId", insertable = false, updatable = false)
    private User userWhoUpdated;

    @OneToMany
    private List<ErrorLog> errorLogs;

    @OneToMany
    private List<EventLog> eventLogs;
}
