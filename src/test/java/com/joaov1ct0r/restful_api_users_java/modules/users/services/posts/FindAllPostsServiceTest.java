package com.joaov1ct0r.restful_api_users_java.modules.users.services.posts;

import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.FindAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.FindAllPostsService;
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
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FindAllPostsServiceTest {
    @InjectMocks
    private FindAllPostsService sut;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    public void beforeEachSetUp() {
        Mockito.reset(this.postRepository);
    }

    @Test
    @DisplayName("Should be albe to get all posts")
    public void shouldBeAbleToGetAllPosts() {
        FindAllPostsDTO query = new FindAllPostsDTO(
                20,
                1,
                "any_content"
        );
        when(
                this.postRepository.findAllByContentContaining(
                        anyString(),
                        any(PageRequest.class)
                )
        ).thenReturn(new PageImpl<>(Collections.emptyList()));

        List<PostDTO> posts = this.sut.execute(query);

        assertThat(posts.isEmpty()).isEqualTo(true);
    }
}
