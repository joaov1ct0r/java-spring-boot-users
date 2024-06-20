package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CreateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateUserServiceTest {
    @InjectMocks
    private CreateUserService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

   @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.passwordEncoder);
        Mockito.reset(this.errorLogsRepository);

        when(this.errorLogsRepository.save(
                new ErrorLogEntity(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        LocalDateTime.now(),
                        400,
                        "any_description"
                )
        )).thenReturn(
                new ErrorLogEntity(
                        UUID.randomUUID(),
                        UUID.randomUUID(),
                        LocalDateTime.now(),
                        400,
                        "any_description"
                )
        );
    }

    @Test
    @DisplayName("Should not be able to register a user with unavailable name")
    public void shouldNotBeAbleToRegisterAUserWithUnavailableName() {
        try {
            CreateUserDTO user = new CreateUserDTO(
                    "any_name",
                    "any_email@mail.com",
                    "any_username",
                    "any_password"
            );
            when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.of(new UserEntity(
                    UUID.randomUUID(),
                    "any_other_name",
                    "any_other_email@mail.com",
                    "any_username",
                    "any_password",
                    LocalDateTime.now(),
                    null,
                    null
            )));

            this.sut.execute(user);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to register a user with unavailable email")
    public void shouldNotBeAbleToRegisterAUserWithUnavailableEmail() {
       try {
            CreateUserDTO user = new CreateUserDTO(
                    "any_name",
                    "any_email@mail.com",
                    "any_username",
                    "any_password"
            );
            when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
            when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(new UserEntity(
                    UUID.randomUUID(),
                    "any_other_name",
                    "any_other_email@mail.com",
                    "any_other_username",
                    "any_other_password",
                    LocalDateTime.now(),
                    null,
                    null
            )));

            this.sut.execute(user);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should be able to register a new user")
    public void shouldBeAbleToRegisterANewUser() throws Exception {
       CreateUserDTO user = new CreateUserDTO(
               "any_name",
               "any_email@mail.com",
               "any_username",
               "any_password"
       );
       when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
       when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
       when(this.passwordEncoder.encode(anyString())).thenReturn("any_password");
       when(this.userRepository.save(any())).thenReturn(
               UserMapper.toPersistence(user)
       );

       var createdUser = this.sut.execute(user);

       assertThat(createdUser).hasFieldOrProperty("id");
    }
}
