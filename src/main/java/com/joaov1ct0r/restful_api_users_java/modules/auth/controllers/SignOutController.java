package com.joaov1ct0r.restful_api_users_java.modules.auth.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/signout/")
@Tag(name = "Auth")
public class SignOutController extends BaseController {
    @GetMapping("/")
    @Operation(summary = "Sign out", description = "É possivel realizar o sign out de um usuário já cadastrado!")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sign out realizado com sucesso!", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            HttpServletResponse res
    ) {
        Object user = req.getAttribute("userId");
        String userId = user.toString();

        Cookie cookie = new Cookie("authorization", null);
        cookie.setDomain("localhost");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setSecure(true);

        res.addCookie(cookie);

        this.generateEventLog(
                UUID.fromString(userId),
                200,
                "Usuário com id: " + userId + "realizou logout"
        );

        ResponseDTO responseDTO = this.responseMapper.toDTO(
                200,
                "Sign out success",
                null
        );

        return ResponseEntity.status(200).body(responseDTO);
    }

}
