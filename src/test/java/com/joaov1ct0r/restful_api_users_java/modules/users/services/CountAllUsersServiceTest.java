package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CountAllUsersDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.CountAllUsersService;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import java.util.Collections;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CountAllUsersServiceTest {
    @InjectMocks
    private CountAllUsersService sut;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
    }

    @Test
    @DisplayName("Should be able to count all users")
    public void shouldBeAbleToCountAllUsers() {
        var query = new CountAllUsersDTO(
                20,
                2,
                "any_name",
                "any_username",
                "any_email@mail.com"
        );
        when(
                this.userRepository.countByNameContainingOrUsernameContainingOrEmailContaining(
                        anyString(),
                        anyString(),
                        anyString(),
                        any(PageRequest.class)
                )
        ).thenReturn(new PageImpl<>(Collections.emptyList()));

        var usersCount = this.sut.execute(query);

        assertThat(usersCount).isEqualTo(0);
    }
}
