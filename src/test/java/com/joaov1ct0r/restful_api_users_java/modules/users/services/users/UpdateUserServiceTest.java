package com.joaov1ct0r.restful_api_users_java.modules.users.services.users;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.ForbiddenException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.UpdateUserService;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdateUserServiceTest {
    @InjectMocks
    private UpdateUserService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.errorLogsRepository);
        Mockito.reset(this.passwordEncoder);

        when(
                this.errorLogsRepository.save(
                        any()
                )
        ).thenReturn(
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
    @DisplayName("Should not be able to update unknown user")
    public void shouldNotBeAbleToUpdateUnknownUser() {
        UserEntity userDTO = new UserEntity(
                UUID.randomUUID(),
                "any_name",
                "any_email",
                "any_username",
                "any_password",
                LocalDateTime.now(),
                null,
                null
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        try {
            this.sut.execute(userDTO, tokenUserId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update other users")
    public void shouldNotBeAbleToUpdateOtherUsers() {
        UserEntity userDTO = new UserEntity(
                UUID.randomUUID(),
                "any_name",
                "any_email",
                "any_username",
                "any_password",
                LocalDateTime.now(),
                null,
                null
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity(
                        UUID.randomUUID(),
                        "any_other_name",
                        "any_other_email@mail.com",
                        "any_other_username",
                        "any_other_password",
                        LocalDateTime.now(),
                        null,
                        null
                )
        ));

        try {
            this.sut.execute(userDTO, tokenUserId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ForbiddenException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update user with unavailable username")
    public void shouldNotBeAbleToUpdateUserWithUnavailableUsername() {
        UserEntity userDTO = new UserEntity(
                UUID.randomUUID(),
                "any_name",
                "any_email",
                "any_username",
                "any_password",
                LocalDateTime.now(),
                null,
                null
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.of(userDTO));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.of(
               new UserEntity(
                        UUID.randomUUID(),
                        "any_other_name",
                        "any_other_email@mail.com",
                        "any_other_username",
                        "any_other_password",
                        LocalDateTime.now(),
                        null,
                        null
                )
        ));

        try {
            this.sut.execute(userDTO, tokenUserId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update user with unavailable email")
    public void shouldNotBeAbleToUpdateUserWithUnavailableEmail() {
        UserEntity userDTO = new UserEntity(
                UUID.randomUUID(),
                "any_name",
                "any_email",
                "any_username",
                "any_password",
                LocalDateTime.now(),
                null,
                null
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.of(userDTO));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(
               new UserEntity(
                        UUID.randomUUID(),
                        "any_other_name",
                        "any_other_email@mail.com",
                        "any_other_username",
                        "any_other_password",
                        LocalDateTime.now(),
                        null,
                        null
                )
        ));

        try {
            this.sut.execute(userDTO, tokenUserId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should be able to update user")
    public void shouldBeAbleToUpdateUser() {
        UserEntity userDTO = new UserEntity(
                UUID.randomUUID(),
                "any_name",
                "any_email",
                "any_username",
                "any_password",
                LocalDateTime.now(),
                null,
                null
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.of(userDTO));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.save(any())).thenReturn(userDTO);

        var updatedUser = this.sut.execute(userDTO, tokenUserId);

        assertThat(updatedUser.getId()).isEqualTo(userDTO.getId());
    }
}
