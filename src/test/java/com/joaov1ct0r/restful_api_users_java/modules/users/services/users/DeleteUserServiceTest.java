package com.joaov1ct0r.restful_api_users_java.modules.users.services.users;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.ForbiddenException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.DeleteUserService;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeleteUserServiceTest {
    @InjectMocks
    private DeleteUserService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.errorLogsRepository);

        when(this.errorLogsRepository.save(any())).thenReturn(
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
    @DisplayName("Should not be able to delete other users")
    public void shouldNotBeAbleToDeleteOtherUsers() {
        String userId = UUID.randomUUID().toString();
        String tokenUserId = UUID.randomUUID().toString();

        try {
            this.sut.execute(
                    userId,
                    tokenUserId
            );
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ForbiddenException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to delete if user isnt found")
    public void shouldNotBeAbleToDeleteIfUserIsntFound() {
        String userId = UUID.randomUUID().toString();
        String tokenUserId = new String(userId);
        when(this.userRepository.findById(any())).thenReturn(
                Optional.empty()
        );

        try {
            this.sut.execute(
                    userId,
                    tokenUserId
            );
        } catch(Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should be able to delete a user")
    public void shouldBeAbleToDeleteAUser() throws Exception{
        String userId = UUID.randomUUID().toString();
        String tokenUserId = new String(userId);

        when(this.userRepository.findById(any())).thenReturn(
                Optional.of(
                       new UserEntity(
                               UUID.fromString(userId),
                               "any_name",
                               "any_email@mail.com",
                               "any_username",
                               "any_password",
                               "any_photo_url",
                               LocalDateTime.now(),
                               LocalDateTime.now(),
                               null
                       )
                )
        );

        UserDTO deletedUser = this.sut.execute(
                userId,
                tokenUserId
        );

        assertThat(deletedUser.getId()).isEqualTo(UUID.fromString(userId));
    }
}
