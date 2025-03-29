package com.joaov1ct0r.restful_api_users_java.modules.users.services.posts;

import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CountAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.CountAllPostsService;
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
public class CountAllPostsServiceTest {
    @InjectMocks
    private CountAllPostsService sut;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.postRepository);
    }

    @Test
    @DisplayName("Should be able to count all posts")
    public void shouldBeAbleToCountAllPosts() {
        CountAllPostsDTO query = new CountAllPostsDTO(
                20,
                1,
                "any_content"
        );
        when(
                this.postRepository.countByContentContaining(
                        anyString(),
                        any(PageRequest.class)
                )
        ).thenReturn(new PageImpl<>(Collections.emptyList()));

        long total = this.sut.execute(query);

        assertThat(total).isEqualTo(0);
    }
}
