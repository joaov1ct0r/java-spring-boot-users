package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class DeleteUserService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    public UserDTO execute(String userId, String tokenUserId) throws Exception {
        boolean isDeletingOtherUser = !Objects.equals(userId, tokenUserId);

        if (isDeletingOtherUser) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    403,
                    "Usuário com id: " + tokenUserId + "tentou excluir outro usuário"
            );
            throw this.forbiddenException("Não permitido");
        }

        Optional<UserEntity> isUserRegistered = this.userRepository.findById(
                UUID.fromString(userId)
        );

        boolean userIsNotRegistered = isUserRegistered.isEmpty();

        if (userIsNotRegistered) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    400,
                    "Usuário com id:" + tokenUserId + "tentou excluir usuário com id: " + userId +
                            ", porem usuário não foi encontrado!");

            throw this.badRequestException("Usuário não encontrado!");
        }

        this.userRepository.deleteById(UUID.fromString(userId));

        return UserMapper.toDTO(isUserRegistered.get());
    }
}
