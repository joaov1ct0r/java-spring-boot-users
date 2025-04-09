package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.ForbiddenException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.domain.services.FileStorageService;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UpdateUserDTO;
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

    @Mock
    private FileStorageService fileStorageService;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.errorLogsRepository);
        Mockito.reset(this.passwordEncoder);
        Mockito.reset(this.fileStorageService);

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
        var userDTO = new UpdateUserDTO(
                UUID.randomUUID().toString(),
                "any_username",
                "any_email",
                "any_name",
                "any_password"
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        try {
            this.sut.execute(userDTO, tokenUserId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update user with unavailable username")
    public void shouldNotBeAbleToUpdateUserWithUnavailableUsername() {
        var userDTO = new UpdateUserDTO(
                "any_username",
                "any_email",
                "any_name",
                "any_password",
                "any_photo_url"
        );
        String tokenUserId = UUID.randomUUID().toString();
        when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity(
                        UUID.fromString(tokenUserId),
                        userDTO.getName(),
                        userDTO.getEmail(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        ));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.of(
               new UserEntity(
                        UUID.randomUUID(),
                        "any_other_name",
                        "any_other_email@mail.com",
                        "any_other_username",
                        "any_other_password",
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        ));

        try {
            this.sut.execute(userDTO, tokenUserId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update user with unavailable email")
    public void shouldNotBeAbleToUpdateUserWithUnavailableEmail() {
        var userDTO = new UpdateUserDTO(
                UUID.randomUUID().toString(),
                "any_username",
                "any_email",
                "any_name",
                "any_password"
        );
        String tokenUserId = UUID.randomUUID().toString();
       when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity(
                        UUID.fromString(tokenUserId),
                        userDTO.getName(),
                        userDTO.getEmail(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        ));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.of(
               new UserEntity(
                        UUID.randomUUID(),
                        "any_other_name",
                        "any_other_email@mail.com",
                        "any_other_username",
                        "any_other_password",
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        ));

        try {
            this.sut.execute(userDTO, tokenUserId, null);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should be able to update user")
    public void shouldBeAbleToUpdateUser() throws Exception {
        var userDTO = new UpdateUserDTO(
                UUID.randomUUID().toString(),
                "any_username",
                "any_email",
                "any_name",
                "any_password"
        );
        String tokenUserId = UUID.randomUUID().toString();
       when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity(
                        UUID.fromString(tokenUserId),
                        userDTO.getName(),
                        userDTO.getEmail(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        ));
        when(this.userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(this.userRepository.save(any())).thenReturn(
                new UserEntity(
                        UUID.fromString(tokenUserId),
                        userDTO.getName(),
                        userDTO.getEmail(),
                        userDTO.getUsername(),
                        userDTO.getPassword(),
                        "any_photo_url",
                        LocalDateTime.now(),
                        null
                )
        );

        var updatedUser = this.sut.execute(userDTO, tokenUserId, null);

        assertThat(updatedUser.getId()).isEqualTo(UUID.fromString(tokenUserId));
    }
}
