package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.EmailService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.Generator;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.ResetPasswordDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResetPasswordService extends BaseService {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Generator generator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void execute(ResetPasswordDTO resetPasswordDTO) throws Exception {
        var isUserRegistered = this.userRepository.findByEmail(resetPasswordDTO.getEmail());

        boolean userIsNotRegistered = isUserRegistered.isEmpty();

        if (userIsNotRegistered) {
            this.generateErrorLog(
                    null,
                    400,
                    "Attempt to reset password from email: " + resetPasswordDTO.getEmail()
            );

            throw this.badRequestException("User not found");
        }

        var userToUpdate = isUserRegistered.get();
        String newUserPassword = this.generator.generateRandomPassword(9);
        userToUpdate.setPassword(this.passwordEncoder.encode(newUserPassword));
        this.userRepository.save(userToUpdate);

        this.emailService.sendMail(
                userToUpdate.getEmail(),
                "Password Updated",
                "Your new password is: " + newUserPassword
        );
    }
}
