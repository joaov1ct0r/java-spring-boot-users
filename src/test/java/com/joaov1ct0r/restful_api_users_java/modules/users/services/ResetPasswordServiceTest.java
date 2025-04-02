package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.EmailService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.Generator;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.ResetPasswordDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.ResetPasswordService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ResetPasswordServiceTest {
    @InjectMocks
    private ResetPasswordService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EmailService emailService;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @Mock
    private Generator generator;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.emailService);
        Mockito.reset(this.errorLogsRepository);
        Mockito.reset(this.passwordEncoder);
        Mockito.reset(this.generator);
        Mockito.when(this.errorLogsRepository.save(any())).thenReturn(new ErrorLogEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                400,
                "any_description"
        ));
    }

    @Test
    @DisplayName("Should not be able to reset password if user is unknown")
    public void shouldNotBeAbleToResetPasswordIfUserIsUnknown() {
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO("any_email@mail.com");
        Mockito.when(this.userRepository.findByEmail(any())).thenReturn(Optional.empty());

        try {
            this.sut.execute(resetPasswordDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);

        }
    }

    @Test
    @DisplayName("Should be able to reset password")
    public void shouldBeAbleToResetPassword() {
        ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO("any_email@mail.com");
        Mockito.when(this.userRepository.findByEmail(any())).thenReturn(Optional.of(
                new UserEntity(
                        UUID.randomUUID(),
                        "any_name",
                        "any_email@mail.com",
                        "any_username",
                        "any_password",
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        ));

        Mockito.when(this.generator.generateRandomPassword(9)).thenReturn("any_generated_string");
        Mockito.when(this.passwordEncoder.encode(anyString())).thenReturn("any_password");

        try {
            this.sut.execute(resetPasswordDTO);
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }
}
