package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
public class UpdateUserService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO execute(UserEntity userDTO, String tokenUserId) {
        var isUserRegistered = this.userRepository.findById(userDTO.getId());

        var userWasntFound = isUserRegistered.isEmpty();

        if (userWasntFound) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    400,
                    "Usuário com id: " + tokenUserId + " tentou editar usuário não encontrado"
            );

            throw this.badRequestException("Usuário não encontrado!");
        }

        var isEditingOtherUser = !userDTO.getId().equals(isUserRegistered.get().getId());

        if (isEditingOtherUser) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    403,
                    "Usuário com id: " + tokenUserId + " tentou editar usuário com id: " + userDTO.getId().toString()
            );

            throw this.forbiddenException("Não permitido!");
        }

        var isUsernameInUseByOtherUser = this.userRepository.findByUsername(userDTO.getUsername());

        var usernameIsntAvailable = isUsernameInUseByOtherUser.isPresent();

        if (usernameIsntAvailable) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    400,
                    "Usuário com id: " + isUserRegistered.get().getId() +
                            " tentou atualizar usuário, porem username: " + userDTO.getUsername() + " já esta em uso"
            );

            throw this.badRequestException("Nome de usuário não esta disponivel");
        }

        var isEmailInUseByOtherUser = this.userRepository.findByEmail(userDTO.getEmail());

        var emailIsntAvailable = isEmailInUseByOtherUser.isPresent();

        if (emailIsntAvailable) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    400,
                    "Usuário com id: " + isUserRegistered.get().getId() +
                            " tentou atualizar usuário, porem email: " + userDTO.getEmail() + " já esta em uso"
            );

            throw this.badRequestException("Email do usuário não esta disponivel");
        }

        var shouldUpdatePassword = !userDTO.getPassword().isEmpty();

        if (shouldUpdatePassword) {
            userDTO.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        }

        UserEntity updatedUser = this.userRepository.save(userDTO);

        return UserMapper.toDTO(updatedUser);
    }
}
