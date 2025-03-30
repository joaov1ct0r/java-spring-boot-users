package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CreatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.mappers.PostMapper;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class CreatePostService extends BaseService {
    @Autowired
    private PostRepository postRepository;

    public PostDTO execute(CreatePostDTO post, UUID userId) throws Exception {
        PostEntity postDTO = PostMapper.toPersistence(
                post,
                userId
        );

        PostEntity postPersistence = this.postRepository.save(postDTO);

        return PostMapper.toDTO(postPersistence);
    }
}
