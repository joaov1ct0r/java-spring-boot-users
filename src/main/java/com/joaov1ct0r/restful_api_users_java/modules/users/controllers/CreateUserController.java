package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.exceptions.BadRequestException;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.EventLogEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.entities.UserEntity;
import com.joaov1ct0r.restful_api_users_java.modules.users.mappers.ResponseMapper;
import com.joaov1ct0r.restful_api_users_java.modules.users.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/signup/")
public class CreateUserController {
    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @PostMapping("/")
    public ResponseEntity<Object> handle(@Valid @RequestBody UserEntity user) {
        try {
            UserDTO createdUser = this.createUserService.execute(user);

            ResponseDTO response = ResponseMapper.toDTO(
                    201,
                    "Usuário criado com sucesso!",
                    createdUser
            );

            this.eventLogsRepository.save(
                    new EventLogEntity(
                            UUID.randomUUID(),
                            createdUser.getId(),
                            LocalDateTime.now(),
                            201,
                            "Usuário com id:" + createdUser.getId() + " criado com sucesso!"
                    )
            );

            return ResponseEntity.status(201).body(response);
        }
        catch(Exception e) {
            if (e instanceof BadRequestException) {
                ResponseDTO response = ResponseMapper.toDTO(
                        400,
                        e.getMessage(),
                        null
                );

                return ResponseEntity.status(400).body(response);
            }

            ResponseDTO response = ResponseMapper.toDTO(
                    500,
                    "Erro Interno",
                    null
            );

            return ResponseEntity.status(500).body(response);
        }
    }
}
