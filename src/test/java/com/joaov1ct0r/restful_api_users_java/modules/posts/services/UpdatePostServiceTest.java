package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.ForbiddenException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.UpdatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
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
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UpdatePostServiceTest {
    @InjectMocks
    private UpdatePostService sut;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.postRepository);
        Mockito.reset(this.userRepository);
        Mockito.reset(this.errorLogsRepository);

        when(
                this.errorLogsRepository.save(
                        any()
                )
        ).thenReturn(
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
    @DisplayName("Should not be able to update a post if user is not found")
    public void shouldNotBeAbleToUpdateAPostIfUserIsNotFound() {
        UUID userId = UUID.randomUUID();
        UpdatePostDTO updatePostDTO = new UpdatePostDTO();

        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        try {
            this.sut.execute(updatePostDTO, userId);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update a post if a post is not found")
    public void shouldNotBeAbleToUpdateAPostIfPostIfNotFound() {
        UUID userId = UUID.randomUUID();
        UpdatePostDTO updatePostDTO = new UpdatePostDTO(UUID.randomUUID().toString(), "any_content");

        when(this.userRepository.findById(any())).thenReturn(
                Optional.of(
                   new UserEntity(
                           userId,
                           "any_name",
                           "any_email@mail.com",
                           "any_username",
                           "any_password",
                           "any_photo_url",
                           LocalDateTime.now(),
                           null
                   )
                )
        );

        when(this.postRepository.findById(any())).thenReturn(Optional.empty());

        try {
            this.sut.execute(updatePostDTO, userId);
        } catch(Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to update a post if user is not owner of post")
    public void shouldNotBeAbleToUpdateAPostIfUserIsNotOwnerOfPost() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UpdatePostDTO updatePostDTO = new UpdatePostDTO(postId.toString(), "any_content");

        when(this.userRepository.findById(any())).thenReturn(
                Optional.of(
                        new UserEntity(
                                userId,
                                "any_name",
                                "any_email@mail.com",
                                "any_username",
                                "any_password",
                                "any_photo_url",
                                LocalDateTime.now(),
                                null
                        )
                )
        );

        when(this.postRepository.findById(any())).thenReturn(Optional.of(
                new PostEntity(
                        postId,
                        "any_other_content",
                        LocalDateTime.now(),
                        null,
                        UUID.randomUUID()
                )
        ));

        try {
            this.sut.execute(updatePostDTO, userId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ForbiddenException.class);
        }
    }

    @Test
    @DisplayName("Should be able to update a post")
    public void shouldBeAbleToUpdateAPost() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();
        UpdatePostDTO updatePostDTO = new UpdatePostDTO(postId.toString(), "any_content");

        when(this.userRepository.findById(any())).thenReturn(
                Optional.of(
                        new UserEntity(
                                userId,
                                "any_name",
                                "any_email@mail.com",
                                "any_username",
                                "any_password",
                                "any_photo_url",
                                LocalDateTime.now(),
                                null
                        )
                )
        );

        when(this.postRepository.findById(any())).thenReturn(Optional.of(
                new PostEntity(
                        postId,
                        "any_other_content",
                        LocalDateTime.now(),
                        null,
                        userId
                )
        ));

        when(this.postRepository.save(any())).thenReturn(
                new PostEntity(
                        postId,
                        updatePostDTO.getContent(),
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        userId
                )
        );

        PostDTO updatedPost = this.sut.execute(updatePostDTO, userId);

        assertThat(updatedPost.getUserWhoCreatedId()).isEqualTo(userId);
    }
}
