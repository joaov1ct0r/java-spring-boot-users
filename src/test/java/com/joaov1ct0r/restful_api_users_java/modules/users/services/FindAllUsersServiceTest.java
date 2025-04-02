package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.FindAllUsersDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.FindAllUsersService;
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
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindAllUsersServiceTest {
    @InjectMocks
    private FindAllUsersService sut;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
    }

    @Test
    @DisplayName("Should be able to get all users")
    public void shouldBeAbleToGetAllUsers() {
        var query = new FindAllUsersDTO(
                20,
                1,
                "any_name",
                "any_username",
                "any_email@mail.com"
        );
        when(
                this.userRepository.findAllByNameContainingOrUsernameContainingOrEmailContaining(
                        anyString(),
                        anyString(),
                        anyString(),
                        any(PageRequest.class)
                )
        ).thenReturn(new PageImpl<>(Collections.emptyList()));

       var users = this.sut.execute(query);

       assertThat(users.isEmpty()).isEqualTo(true);
    }
}
