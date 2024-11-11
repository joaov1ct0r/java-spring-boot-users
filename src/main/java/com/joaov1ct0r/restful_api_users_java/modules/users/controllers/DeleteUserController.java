package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.DeleteUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.DeleteUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/")
@Tag(name = "Usuário")
public class DeleteUserController extends BaseController {
    @Autowired
    private DeleteUserService deleteUserService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @DeleteMapping("/")
    @Operation(summary = "Deletar um usuário", description = "É possivel deletar um usuário já cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não permitido"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")

    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @Valid @RequestBody DeleteUserDTO dto
    ) throws Exception{
        Object userId = req.getAttribute("userId");
        String tokenUserId = userId.toString();

        UserDTO deletedUser = this.deleteUserService.execute(
                dto.getUserId(),
                tokenUserId
        );

        ResponseDTO response = this.responseMapper.toDTO(
                204,
                "User deleted with success",
                deletedUser
        );

        this.generateEventLog(
                null,
                204,
                "Usuário com id: " + deletedUser.getId() + " deletado com sucesso!"
        );

        return ResponseEntity.status(204).body(response);
    }
}
