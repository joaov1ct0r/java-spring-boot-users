package com.joaov1ct0r.restful_api_users_java.modules.users.services.auth;

import com.joaov1ct0r.restful_api_users_java.modules.auth.services.CreateCookieService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreateCookieServiceTest {

    @InjectMocks
    private CreateCookieService sut;

    @Test
    @DisplayName("should be able to create a new cookie")
    public void shouldBeAbleToCreateANewCookie() {
        String cookieName = "any_cookie_name";
        String domainName = "any_domain_name";

        var createdCookie = this.sut.execute(cookieName, domainName);

        assertThat(createdCookie.getName()).isEqualTo(cookieName);
    }
}
