package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.mappers.PostMapper;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeletePostService extends BaseService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public PostDTO execute(UUID postId, UUID userId) {
        Optional<UserEntity> user = this.userRepository.findById(userId);

        boolean userIsNotFound = user.isEmpty();

        if (userIsNotFound) {
            this.generateErrorLog(
                    userId,
                    400,
                    "Usuário com id: " + userId + " tentou deletar post, porem usuário não foi encontrado!"
            );

            throw this.badRequestException("Usuário não encontrado!");
        }

        Optional<PostEntity> post = this.postRepository.findById(postId);

        boolean postIsNotFound = post.isEmpty();

        if (postIsNotFound) {
            this.generateErrorLog(
                    userId,
                    400,
                    "Usuário com id: " + userId + " tentou excluir post com id: " + postId + " porem post não foi encontrado"
            );

            throw this.badRequestException("Post não encontrado!");

        }

        boolean userIsNotOwnerOfPost = post.get().getUserWhoCreatedId() != user.get().getId();

        if (userIsNotOwnerOfPost) {
            this.generateErrorLog(
                    userId,
                    403,
                    "Usuário com id: " + userId + " tentou excluir post com id: " + postId + ", porem não possui permissão!"
            );

            throw this.forbiddenException("Não permitido!");
        }

        this.postRepository.deleteById(postId);

        return PostMapper.toDTO(post.get());
    }
}
