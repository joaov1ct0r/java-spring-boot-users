package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.ResetPasswordDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.ResetPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/reset_password/")
@Tag(name = "Usuário")
public class ResetPasswordController extends BaseController {
    @Autowired
    private ResetPasswordService resetPasswordService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @PutMapping("/")
    @Operation(summary = "Resetar senha do usuário", description = "É possivel resetar a senha de um usuário existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Senha resetada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro da requisição"),
            @ApiResponse(responseCode = "403", description = "Não permitido"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity<Object> handle(
            @RequestBody @Valid ResetPasswordDTO resetPasswordDTO
            ) throws Exception {
        this.resetPasswordService.execute(resetPasswordDTO);

        this.generateEventLog(
                null,
                204,
                "Solicitação de reset de password  do email: " + resetPasswordDTO.getEmail() + " realizada com sucesso!"
        );

        ResponseDTO response = this.responseMapper.toDTO(
                204,
                "New password was sent to the registered email",
                null
        );

        return ResponseEntity.status(204).body(response);
    }
}
