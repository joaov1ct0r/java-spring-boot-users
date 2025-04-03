package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.entities.ErrorLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.exceptions.ForbiddenException;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class DeletePostServiceTest {
    @InjectMocks
    private DeletePostService sut;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.userRepository);
        Mockito.reset(this.postRepository);
        Mockito.reset(this.errorLogsRepository);

        when(this.errorLogsRepository.save(any())).thenReturn(
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
    @DisplayName("Should not be able to delete post if user is not found")
    public void shouldNotBeAbleToDeletePostIfUserIsNotFound() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        when(this.userRepository.findById(any())).thenReturn(Optional.empty());

        try {
            this.sut.execute(postId, userId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to delete post if post is not found")
    public void shouldNotBeAbleToDeletePostIfPostIsNotFound() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity()
        ));

        when(this.postRepository.findById(any())).thenReturn(
                Optional.empty()
        );

        try {
            this.sut.execute(postId, userId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(BadRequestException.class);
        }
    }

    @Test
    @DisplayName("Should not be able to delete post if user is not the owner")
    public void shouldNotBeAbleToDeletePostIfUserIsNotTheOwner() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity(
                        userId,
                        "any_name",
                        "any_email@mail.com",
                        "any_username",
                        "any_password",
                        null,
                        LocalDateTime.now(),
                        null
                )
        ));

        when(this.postRepository.findById(any())).thenReturn(
                Optional.of(
                        new PostEntity(
                                postId,
                                "any_content",
                                LocalDateTime.now(),
                                null,
                                UUID.randomUUID()
                        )
                )
        );

        try {
            this.sut.execute(postId, userId);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(ForbiddenException.class);
        }
    }

    @Test
    @DisplayName("Should be able to delete a post")
    public void shouldBeAbleToDeleteAPost() {
        UUID userId = UUID.randomUUID();
        UUID postId = UUID.randomUUID();

        when(this.userRepository.findById(any())).thenReturn(Optional.of(
                new UserEntity(
                        userId,
                        "any_name",
                        "any_email@mail.com",
                        "any_username",
                        "any_password",
                        null,
                        LocalDateTime.now(),
                        null
                )
        ));

        when(this.postRepository.findById(any())).thenReturn(
                Optional.of(
                        new PostEntity(
                                postId,
                                "any_content",
                                LocalDateTime.now(),
                                null,
                                userId
                        )
                )
        );

        PostDTO deletedPost = this.sut.execute(postId, userId);

        assertThat(deletedPost.getUserWhoCreatedId()).isEqualByComparingTo(userId);
    }
}
