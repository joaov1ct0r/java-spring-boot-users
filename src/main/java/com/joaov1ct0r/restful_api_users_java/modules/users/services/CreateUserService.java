package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.S3Service;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CreateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CreateUserService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private S3Service s3Service;

    public UserDTO execute(CreateUserDTO user, @Nullable MultipartFile file) throws Exception {
        boolean nameIsInUseByOtherUser = this.userRepository.findByUsername(user.getUsername()).isPresent();

        if (nameIsInUseByOtherUser) {
            this.generateErrorLog(
                    null,
                    400,
                    "Usuário tentou fazer registro com 'username' " + user.getUsername() + ", porem username já esta em uso"
            );

            throw this.badRequestException("Nome do usuário não esta disponivel!");
        }

        boolean emailIsInUseByOtherUser = this.userRepository.findByEmail(user.getEmail()).isPresent();

        if (emailIsInUseByOtherUser) {
            this.generateErrorLog(
                    null,
                    400,
                    "Usuário tentou fazer registro com 'email' " + user.getEmail() + ", porem email já esta em uso"

            );

            throw this.badRequestException("Email do usuário não esta disponivel!");
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        boolean fileIsPresent = file != null;

        if (fileIsPresent) {
            String fileName = this.s3Service.uploadFile(file);
            user.setPhotoUrl(fileName);
        }

        UserEntity userDTO = UserMapper.toPersistence(user);

        UserEntity createdUser = this.userRepository.save(userDTO);

        return UserMapper.toDTO(createdUser);
    }
}
