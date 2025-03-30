package com.joaov1ct0r.restful_api_users_java.modules.posts.mappers;

import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CreatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import java.time.LocalDateTime;
import java.util.UUID;

public class PostMapper {
    public static PostDTO toDTO(PostEntity post) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setContent(post.getContent());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
        postDTO.setUserWhoCreatedId(post.getUserWhoCreatedId());
        postDTO.setUserWhoUpdatedId(post.getUserWhoUpdatedId());
        return postDTO;
    }

    public static PostEntity toPersistence(CreatePostDTO postDTO, UUID userWhoCreatedId) {
        return new PostEntity(
                UUID.randomUUID(),
                postDTO.getContent(),
                LocalDateTime.now(),
                null,
                userWhoCreatedId,
                null
        );
    }
}
