package com.joaov1ct0r.restful_api_users_java.modules.users.repositories;

import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> countByNameContainingOrUsernameContainingOrEmailContaining(
            String name,
            String username,
            String email,
            Pageable pageable
    );
    Page<UserEntity> findAllByNameContainingOrUsernameContainingOrEmailContaining(
            String name,
            String username,
            String email,
            Pageable pageable
    );
}