package com.joaov1ct0r.restful_api_users_java.modules.users.entities;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.EventLogEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity(name = "users")
public class UserEntity implements UserDetails {
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userWhoUpdatedId", insertable = false, updatable = false)
    private UserEntity userWhoUpdated;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<ErrorLogEntity> errorLogs;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
    private List<EventLogEntity> eventLogs;

    public UserEntity() {}

    public UserEntity(
            UUID id,
            String name,
            String email,
            String username,
            String password,
            LocalDateTime createdAt,
            @Nullable LocalDateTime updatedAt,
            @Nullable UUID userWhoUpdatedId
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.userWhoUpdatedId = userWhoUpdatedId;
    }

    @Nullable
    public UUID getUserWhoUpdatedId() {
        return userWhoUpdatedId;
    }

    public void setUserWhoUpdatedId(@Nullable UUID userWhoUpdatedId) {
        this.userWhoUpdatedId = userWhoUpdatedId;
    }

    @Nullable
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(@Nullable LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Email String getEmail() {
        return email;
    }

    public void setEmail(@Email String email) {
        this.email = email;
    }

    public @Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço") String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(@Pattern(regexp = "\\S+", message = "O campo [username] não deve conter espaço") String username) {
        this.username = username;
    }

    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
