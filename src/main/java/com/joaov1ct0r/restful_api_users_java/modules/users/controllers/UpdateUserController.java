package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UpdateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.UpdateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequestMapping("/user/")
@Tag(name = "Usuário")
public class UpdateUserController extends BaseController {
    @Autowired
    private UpdateUserService updateUserService;

    @PutMapping("/")
    @Operation(summary = "Editar um usuário", description = "É possivel editar um usuário já cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário editado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro da requisição"),
            @ApiResponse(responseCode = "403", description = "Não permitido"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @RequestPart("user") @Valid UpdateUserDTO userDTO,
            @Nullable @RequestParam("file")MultipartFile file
            ) throws Exception {
        Object userIdAtt = req.getAttribute("userId");
        String tokenUserId = userIdAtt.toString();

        var updatedUser = this.updateUserService.execute(userDTO, tokenUserId, file);

        this.generateEventLog(
                UUID.fromString(tokenUserId),
                204,
                "Usuário atualizado com sucesso!"
        );

        ResponseDTO response = this.responseMapper.toDTO(
                204,
                "User updated with success",
                updatedUser
        );

        return ResponseEntity.status(204).body(response);
    }
}
