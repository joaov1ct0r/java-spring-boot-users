package com.joaov1ct0r.restful_api_users_java.modules.users.controllers.users;

import java.time.LocalDateTime;
import java.util.UUID;
import com.github.javafaker.Faker;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateUserControllerTest {
    private MockMvc mvc;

    private Faker faker;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        faker = new Faker();
    }

    @Test
    public void shouldBeAbleToCreateNewUser() throws Exception {
        UUID userId = UUID.randomUUID();
        var user = new UserEntity(
                userId,
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.esports().player(),
                faker.internet().password(),
                "any_photo_url",
                LocalDateTime.now(),
                null,
                null

        );

        var createUserJson = TestUtils.stringToMMF(user);

        mvc.perform(MockMvcRequestBuilders.multipart("/signup/")
                .file(createUserJson)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .with(request -> {
                    request.setMethod("POST");
                    return request;
                })
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}