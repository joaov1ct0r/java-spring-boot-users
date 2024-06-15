package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class CreateUserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ErrorLogsRepository errorLogsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO execute(UserEntity user) throws BadRequestException {
        boolean nameIsInUseByOtherUser = this.userRepository.findByUsername(user.getUsername()).isPresent();

        if (nameIsInUseByOtherUser) {
            ErrorLogEntity errorLog = new ErrorLogEntity(
                    UUID.randomUUID(),
                    null,
                    LocalDateTime.now(),
                    400,
                    "Usuário tentou fazer registro com 'username' " + user.getUsername() + ", porem username já esta em uso"

            );
            this.errorLogsRepository.save(errorLog);

            throw new BadRequestException("Nome do usuário não esta disponivel!");
        }

        boolean emailIsInUseByOtherUser = this.userRepository.findByEmail(user.getEmail()).isPresent();

        if (emailIsInUseByOtherUser) {
            ErrorLogEntity errorLog = new ErrorLogEntity(
                    UUID.randomUUID(),
                    null,
                    LocalDateTime.now(),
                    400,
                    "Usuário tentou fazer registro com 'email' " + user.getEmail() + ", porem email já esta em uso"
            );
           this.errorLogsRepository.save(errorLog);

            throw new BadRequestException("Email do usuário não esta disponivel!");
        }

        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        UserEntity createdUser = this.userRepository.save(user);

        return UserMapper.toDTO(createdUser);
    }
}
