package com.joaov1ct0r.restful_api_users_java.modules.users.services;

import com.joaov1ct0r.restful_api_users_java.modules.domain.services.BaseService;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.FindAllUsersDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.UserMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FindAllUsersService extends BaseService {
    @Autowired
    private UserRepository userRepository;

    public List<UserDTO> execute(FindAllUsersDTO query) {
        boolean isQuery = query.getEmail() != null || query.getName() != null || query.getUsername() != null;

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        var page = PageRequest.of(query.getPage() - 1, query.getPerPage(), sort);

        Page<UserEntity> users;

        if (isQuery) {
            users = this.userRepository.findAllByNameContainingOrUsernameContainingOrEmailContaining(
                    query.getName(),
                    query.getUsername(),
                    query.getEmail(),
                    page
            );
        } else {
            users = this.userRepository.findAll(page);
        }

        return users.stream().map(UserMapper::toDTO).toList();
    }
}
