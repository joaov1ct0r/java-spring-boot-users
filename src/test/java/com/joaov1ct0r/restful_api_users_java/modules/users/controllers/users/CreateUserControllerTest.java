package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

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
        var user = new UserEntity(
                UUID.randomUUID(),
                faker.name().firstName(),
                faker.internet().emailAddress(),
                faker.esports().player(),
                faker.internet().password(),
                LocalDateTime.now(),
                null,
                null

        );

        mvc.perform(MockMvcRequestBuilders.post("/signup/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJson(user))
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }
}