package com.joaov1ct0r.restful_api_users_java.modules.users.controllers.users;

import com.github.javafaker.Faker;
import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.DeleteUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.utils.TestUtils;
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
import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DeleteUserControllerTest {
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
    public void shouldBeAbleToDeleteAUser() throws Exception {
        UUID userId = UUID.randomUUID();
        var createUserDTO = new UserEntity(
                userId,
                faker.name().username(),
                faker.internet().emailAddress(),
                faker.name().firstName(),
                faker.internet().password(),
                "any_photo_url",
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
        var createUserJson = TestUtils.stringToMMF(createUserDTO);
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
        UserDTO userDTO = TestUtils.jsonToUserDTO(response.getResource().toString(), UserDTO.class);

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

        DeleteUserDTO deleteUserDTO = new DeleteUserDTO(userDTO.getId().toString());

        var deleteUserResponse = mvc.perform(
                MockMvcRequestBuilders.delete("/user/")
                        .cookie(cookieAuthorization)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtils.objectToJson(deleteUserDTO))
        ).andReturn().getResponse();

        int deleteUserResponseStatusCode = deleteUserResponse.getStatus();

        assertThat(deleteUserResponseStatusCode).isEqualTo(204);
    }
}
