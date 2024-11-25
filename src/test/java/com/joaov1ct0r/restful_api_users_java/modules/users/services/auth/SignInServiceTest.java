package com.joaov1ct0r.restful_api_users_java.modules.users.services.auth;

import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.SignInService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.UnauthorizedException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CreateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class SignInServiceTest {
    @InjectMocks
    private SignInService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.passwordEncoder);
        Mockito.reset(this.errorLogsRepository);

        when(this.errorLogsRepository.save(any())).thenReturn(new ErrorLogEntity(
                UUID.randomUUID(),
                UUID.randomUUID(),
                LocalDateTime.now(),
                400,
                "any_description"
        ));
    }

    @Test
    @DisplayName("Should not be able to authenticate invalid username")
    public void shouldNotBeAbleToAuthenticateWithInvalidUsername() {
        try {
            SignInDTO user = new SignInDTO("any_username", "any_password");
            when(this.userRepository.findByUsername(any())).thenReturn(Optional.empty());

            this.sut.execute(user);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }

    }

    @Test
    @DisplayName("Should not be able to authenticate with invalid password")
    public void shouldNotBeAbleToAuthenticateWithInvalidPassword() {
        try {
           SignInDTO user = new SignInDTO("any_username", "any_password");
           when(this.userRepository.findByUsername(any())).thenReturn(Optional.of(
                   UserMapper.toPersistence(
                           new CreateUserDTO(
                                   user.getUsername(),
                                   "any_email",
                                   "any_name",
                                   user.getPassword(),
                                   "any_photo_url"
                           )
                   )
           ));
           when(this.passwordEncoder.matches(any(), any())).thenReturn(false);

           this.sut.execute(user);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(UnauthorizedException.class);
        }
    }

    @Test
    @DisplayName("Should be able to authenticate user")
    public void shouldBeAbleToAuthenticateUser() throws Exception {
        SignInDTO user = new SignInDTO("any_username", "any_password");
        when(this.userRepository.findByUsername(any())).thenReturn(Optional.of(
                UserMapper.toPersistence(
                        new CreateUserDTO(
                                user.getUsername(),
                                "any_email",
                                "any_name",
                                user.getPassword(),
                                "any_photo_url"
                        )
                )
        ));
        when(this.passwordEncoder.matches(any(), any())).thenReturn(true);

        var authenticatedUser = this.sut.execute(user);

        assertThat(authenticatedUser).hasFieldOrProperty("id");
    }
}
