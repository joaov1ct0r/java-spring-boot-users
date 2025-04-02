package com.joaov1ct0r.restful_api_users_java.modules.posts.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CountAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.entities.PostEntity;
import com.joaov1ct0r.restful_api_users_java.modules.posts.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CountAllPostsService extends BaseService {
    @Autowired
    private PostRepository postRepository;

    public long execute(CountAllPostsDTO query) {
        boolean isQuery = query.getContent() != null;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest page = PageRequest.of(query.getPage() - 1, query.getPerPage(), sort);

        Page<PostEntity> posts;

        if (isQuery) {
            posts = this.postRepository.countByContentContaining(query.getContent(), page);
        } else {
            posts = this.postRepository.findAll(page);
        }

        return posts.stream().count();
    }
}
