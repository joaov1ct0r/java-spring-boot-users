package com.joaov1ct0r.restful_api_users_java.modules.posts.controllers;

import com.github.javafaker.Faker;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CreatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.UpdatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
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
import java.time.LocalDateTime;
import java.util.UUID;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UpdatePostControllerTest {
    private MockMvc mvc;

    private Faker faker;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(SecurityMockMvcConfigurers.springSecurity())
                .build();

        this.faker = new Faker();
    }

    @Test
    public void shouldBeAbleToUpdateAPost() throws Exception {
        UUID userId = UUID.randomUUID();
        var user = new UserEntity(
                userId,
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.internet().password(),
                "any_photo_url",
                LocalDateTime.now(),
                null
        );

        var createUserJson = TestUtils.stringToMMF(user);
        var createUserResponse = mvc.perform(
                MockMvcRequestBuilders.multipart("/signup/")
                        .file(createUserJson)
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .with(request -> {
                            request.setMethod("POST");
                            return request;
                        })
        ).andReturn().getResponse().getContentAsString();
        ResponseDTO response = TestUtils.jsonToObject(createUserResponse, ResponseDTO.class);
        assert response.getResource() != null;
        assert response.getStatusCode().equals(201);
        UserDTO userDTO = TestUtils.jsonToUserDTO(response.getResource().toString(), UserDTO.class);

        var signInDTO = new SignInDTO(
                user.getUsername(),
                user.getPassword()
        );
        Cookie cookieAuthorization = mvc.perform(
                MockMvcRequestBuilders.post("/signin/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(signInDTO))
        ).andReturn().getResponse().getCookie("authorization");

        assert cookieAuthorization != null;

        CreatePostDTO post = new CreatePostDTO("any_content");

        String createPostResponse = this.mvc.perform(MockMvcRequestBuilders.post("/post/")
                .cookie(cookieAuthorization)
                .content(TestUtils.objectToJson(post))
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse().getContentAsString();

        ResponseDTO createdPostResponseDTO = TestUtils.jsonToObject(createPostResponse, ResponseDTO.class);
        assert createdPostResponseDTO.getResource() != null;

        PostDTO createdPost = TestUtils.jsonToPostDTO(createdPostResponseDTO.getResource().toString(), PostDTO.class);

        UpdatePostDTO updatePostDTO = new UpdatePostDTO(
                createdPost.getId().toString(),
                "any other content"
        );

        var updatePostResponse = this.mvc.perform(
                MockMvcRequestBuilders.put("/post/")
                        .cookie(cookieAuthorization)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(updatePostDTO))
        ).andReturn().getResponse();

        int updatePostResponseStatusCode = updatePostResponse.getStatus();

        assertThat(updatePostResponseStatusCode).isEqualTo(204);
    }
}
