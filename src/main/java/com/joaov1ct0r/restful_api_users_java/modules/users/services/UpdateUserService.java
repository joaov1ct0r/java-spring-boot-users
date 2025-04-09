package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.FileStorageService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.S3Service;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UpdateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UpdateUserService extends BaseService {
    @Value("${SPRING_PROFILES_ACTIVE:prod}")
    private String env;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private FileStorageService fileStorageService;

    public UserDTO execute(UpdateUserDTO userDTO, String tokenUserId, @Nullable MultipartFile file) throws Exception {
        var isUserRegistered = this.userRepository.findById(UUID.fromString(tokenUserId));

        var userWasntFound = isUserRegistered.isEmpty();

        if (userWasntFound) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    400,
                    "Usuário com id: " + tokenUserId + " tentou editar usuário não encontrado"
            );

            throw this.badRequestException("Usuário não encontrado!");
        }

        var isUsernameInUseByOtherUser = this.userRepository.findByUsername(userDTO.getUsername());

        var usernameIsntAvailable = isUsernameInUseByOtherUser.isPresent() && !isUsernameInUseByOtherUser.get().getId().equals(isUserRegistered.get().getId());

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

        var emailIsntAvailable = isEmailInUseByOtherUser.isPresent() && !isEmailInUseByOtherUser.get().getId().equals(isUserRegistered.get().getId());

        if (emailIsntAvailable) {
            this.generateErrorLog(
                    UUID.fromString(tokenUserId),
                    400,
                    "Usuário com id: " + isUserRegistered.get().getId() +
                            " tentou atualizar usuário, porem email: " + userDTO.getEmail() + " já esta em uso"
            );

            throw this.badRequestException("Email do usuário não esta disponivel");
        }

        var userToUpdate = isUserRegistered.get();

        var shouldUpdatePassword = userDTO.getPassword() != null;

        if (shouldUpdatePassword) {
            userToUpdate.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));
        }

        boolean fileIsPresent = file != null;

        if (fileIsPresent && this.env.equals("prod")) {
            String fileName = this.s3Service.uploadFile(file);
            userToUpdate.setPhotoUrl(fileName);
        }

        if (fileIsPresent && this.env.equals("dev")) {
            String fileName = this.fileStorageService.storeFile(file);
            userToUpdate.setPhotoUrl(fileName);
        }

        userToUpdate.setName(userDTO.getName());
        userToUpdate.setEmail(userDTO.getEmail());
        userToUpdate.setUsername(userDTO.getUsername());
        userToUpdate.setUpdatedAt(LocalDateTime.now());

        UserEntity updatedUser = this.userRepository.save(userToUpdate);

        return UserMapper.toDTO(updatedUser);
    }
}
