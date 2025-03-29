package com.joaov1ct0r.restful_api_users_java.modules.posts.repositories;

import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostEntity, UUID> {
    Page<PostEntity> findAllByContentContaining(String content, Pageable pageable);
    Page<PostEntity> countByContentContaining(String content, Pageable pageable);
}
