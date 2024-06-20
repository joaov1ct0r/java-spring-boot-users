package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CreateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup/")
public class CreateUserController extends BaseController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @PostMapping("/")
    public ResponseEntity<Object> handle(@Valid @RequestBody CreateUserDTO user) throws Exception {
        UserDTO createdUser = this.createUserService.execute(user);

        ResponseDTO response = this.responseMapper.toDTO(
                201,
                "Usuário criado com sucesso!",
                createdUser
        );

        this.generateEventLog(
                createdUser.getId(),
                201,
                "Usuário com id:" + createdUser.getId() + " criado com sucesso!"
        );

        return ResponseEntity.status(201).body(response);
    }
}
