package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.UpdatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.mappers.PostMapper;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdatePostService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    public PostDTO execute(UpdatePostDTO postDTO, UUID userId) {
        Optional<UserEntity> user = this.userRepository.findById(userId);

        boolean userIsNotFound = user.isEmpty();

        if (userIsNotFound) {
            this.generateErrorLog(
                    userId,
                    400,
                    "Usuário com id: " + userId.toString() + " tentou atualizar um post, porem usuário não foi encontrado!"
            );

            throw this.badRequestException("Usuário não encontrado!");
        }

        Optional<PostEntity> post = this.postRepository.findById(UUID.fromString(postDTO.getId()));

        boolean postIsNotFound = post.isEmpty();

        if (postIsNotFound) {
            this.generateErrorLog(
                    userId,
                    400,
                    "Usuário com id: " + userId + " tentou atualizar um post, porem post não foi encontrado!"
            );

            throw this.badRequestException("Post não encontrado!");
        }

        boolean userIsOwnerOfPost = user.get().getId().equals(post.get().getUserWhoCreatedId());

        if (!userIsOwnerOfPost) {
            this.generateErrorLog(
                    userId,
                    403,
                    "Usuário com id: " + userId + " tentou atualizar um post, porem não tem permissão"
            );

            throw this.forbiddenException("Não permitido!");
        }

        PostEntity postToUpdate = post.get();
        postToUpdate.setContent(postDTO.getContent());
        postToUpdate.setUpdatedAt(LocalDateTime.now());

        PostEntity updatedPost = this.postRepository.save(postToUpdate);

        return PostMapper.toDTO(updatedPost);
    }
}
