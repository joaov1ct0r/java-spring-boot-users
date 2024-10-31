package com.joaov1ct0r.restful_api_users_java.modules.auth.services;

import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SignInService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO execute(SignInDTO credentials) throws Exception {
        var userIsRegistered = this.userRepository.findByUsername(credentials.getUsername());

        boolean userIsNotFound = userIsRegistered.isPresent() == false;

        if (userIsNotFound) {
            this.generateErrorLog(
                    null,
                    400,
                    "Usuário tentou realizar login com username: " + credentials.getUsername() + ", porem username não esta registrado"
            );

            throw this.badRequestException("Nome de usuário e ou senha incorretos");
        }

        var authUser = userIsRegistered.get();

        var passwordsMatches = this.passwordEncoder.matches(credentials.getPassword(), authUser.getPassword());

        var passwordsDoesntMatches = !passwordsMatches;

        if (passwordsDoesntMatches) {
            this.generateErrorLog(
                    authUser.getId(),
                    401,
                    "Usuário com username " + authUser.getUsername() + ", tentou realizar login com senha incorreta"
            );

            throw this.badRequestException("Nome de usuário e ou senha incorretos");
        }

        return UserMapper.toDTO(authUser);
    }
}
