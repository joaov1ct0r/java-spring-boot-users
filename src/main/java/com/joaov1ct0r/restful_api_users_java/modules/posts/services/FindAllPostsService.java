package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.FindAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.mappers.PostMapper;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindAllPostsService extends BaseService {
    @Autowired
    private PostRepository postRepository;

    public List<PostDTO> execute(FindAllPostsDTO query) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest page = PageRequest.of(query.getPage() - 1, query.getPerPage(), sort);
        Page<PostEntity> posts = this.postRepository.findAllByContentContaining(
                query.getContent(),
                page
        );

        return posts.stream().map(PostMapper::toDTO).toList();
    };
}
