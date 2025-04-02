package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CreatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.mappers.PostMapper;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.CreatePostService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.ErrorLogsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import java.util.UUID;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CreatePostServiceTest {
    @InjectMocks
    private CreatePostService sut;

    @Mock
    private PostRepository postRepository;

    @Mock
    private ErrorLogsRepository errorLogsRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.postRepository);
        Mockito.reset(this.errorLogsRepository);
    }

    @Test
    @DisplayName("Should be able to register a new post")
    public void shouldBeAbleToRegisterANewPost() throws Exception {
        UUID userId = UUID.randomUUID();
        CreatePostDTO post = new CreatePostDTO(
                "any_content"
        );
        when(this.postRepository.save(any())).thenReturn(
                PostMapper.toPersistence(post, userId)
        );

        var createdPost = this.sut.execute(post, userId);

        assertThat(createdPost).hasFieldOrProperty("id");
    }
}
