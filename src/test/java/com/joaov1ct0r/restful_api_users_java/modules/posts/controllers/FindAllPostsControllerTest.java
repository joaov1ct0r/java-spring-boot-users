package com.joaov1ct0r.restful_api_users_java.modules.posts.controllers;

import com.github.javafaker.Faker;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.FindAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.utils.TestUtils;
import jakarta.servlet.http.Cookie;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import java.time.LocalDateTime;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FindAllPostsControllerTest {
    private MockMvc mvc;

    private Faker faker;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        this.faker = new Faker();
    }

    @Test
    public void shouldBeAbleToFindAllPosts() throws Exception {
        UUID userId = UUID.randomUUID();
        var createUserDTO = new UserEntity(
                userId,
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.internet().password(),
                "any_photo_url",
                LocalDateTime.now(),
                null
        );

        var createUserJson = TestUtils.stringToMMF(createUserDTO);
        mvc.perform(
                MockMvcRequestBuilders.multipart("/signup/")
                        .file(createUserJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.setMethod("POST");
                            return request;
                        })
        ).andReturn().getResponse();

        var signInDTO = new SignInDTO(
                createUserDTO.getUsername(),
                createUserDTO.getPassword()
        );
        Cookie cookieAuthorization = mvc.perform(
                MockMvcRequestBuilders.post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(signInDTO))
        ).andReturn().getResponse().getCookie("authorization");

        assert cookieAuthorization != null;

        FindAllPostsDTO findAllPostsDTO = new FindAllPostsDTO(
                20,
                1,
                "any_content"
        );

        var findAllPostsResponse = mvc.perform(
                MockMvcRequestBuilders.get("/post/")
                        .cookie(cookieAuthorization)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(findAllPostsDTO))
        ).andReturn().getResponse();

        int findAllPostsResponseStatusCode = findAllPostsResponse.getStatus();

        assertThat(findAllPostsResponseStatusCode).isEqualTo(200);
    }
}
