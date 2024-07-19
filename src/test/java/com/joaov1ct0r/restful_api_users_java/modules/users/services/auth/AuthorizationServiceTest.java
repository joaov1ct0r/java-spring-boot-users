package com.joaov1ct0r.restful_api_users_java.modules.users.services.auth;

import com.joaov1ct0r.restful_api_users_java.modules.auth.services.AuthorizationService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.UnauthorizedException;
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
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthorizationServiceTest {
    @InjectMocks
    private AuthorizationService sut;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
    }

    @Test
    @DisplayName("should not have authorization with unknown username")
    public void shouldNotHaveAuthorizationWithUnknownUsername() {
        String username = "any_username";
        when(this.userRepository.findByUsername(any())).thenReturn(Optional.empty());

        try {
            this.sut.loadUserByUsername(username);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(UnauthorizedException.class);
        }
    }

    @Test
    @DisplayName("should have authorization with known username")
    public void shouldHaveAuthorizationWithKnownUsername() {
        String username = "any_username";
        when(this.userRepository.findByUsername(any())).thenReturn(
                Optional.of(
                        UserMapper.toPersistence(
                                new CreateUserDTO(
                                        username,
                                        "any_email@mail.com",
                                        "any_name",
                                        "any_password"
                                )
                        )
                )
        );

        UserDetails user = this.sut.loadUserByUsername(username);

        assertThat(user.getUsername()).isEqualTo(username);
    }
}
