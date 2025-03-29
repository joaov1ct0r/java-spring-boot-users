package com.joaov1ct0r.restful_api_users_java.modules.posts.mappers;

import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;

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
}
