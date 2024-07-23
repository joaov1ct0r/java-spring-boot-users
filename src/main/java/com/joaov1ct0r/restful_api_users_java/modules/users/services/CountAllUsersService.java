package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CountAllUsersDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CountAllUsersService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    public long execute(CountAllUsersDTO query)
    {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        var page = PageRequest.of(query.getPage() - 1, query.getPerPage(), sort);
        var users = this.userRepository.countByNameContainingAndUsernameContainingAndEmailContaining(
                query.getName(),
                query.getUsername(),
                query.getEmail(),
                page
        );

        return users.stream().count();
    }
}
