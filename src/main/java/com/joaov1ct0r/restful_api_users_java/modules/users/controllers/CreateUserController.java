package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CreateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.CreateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup/")
@Tag(name = "Usuário")
public class CreateUserController extends BaseController {

    @Autowired
    private CreateUserService createUserService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @PostMapping("/")
    @Operation(summary = "Criar um novo usuário", description = "É possivel realizar a criação de um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso!", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Erro da requisição (email e ou username não disponivel)"),
            @ApiResponse(responseCode = "500", description = "Erro Interno"),
    })
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
