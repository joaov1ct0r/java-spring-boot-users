package com.joaov1ct0r.restful_api_users_java.modules.auth.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.CreateCookieService;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.CreateJWTTokenService;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.SignInService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin/")
@Tag(name = "Auth")
public class SignInController extends BaseController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SignInService signInService;

    @Autowired
    private CreateJWTTokenService createJWTTokenService;

    @Autowired
    private CreateCookieService createCookieService;

    @PostMapping("/")
    @Operation(summary = "Sign in", description = "É possivel realizar o sign in de um usuário já cadastrado!")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sign in realizado com sucesso!", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class))
            }),
            @ApiResponse(responseCode = "400", description = "Erro da requisição"),
            @ApiResponse(responseCode = "401", description = "Não autorizado"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity<Object> handle(
            HttpServletResponse response,
            @Valid @RequestBody SignInDTO credentials
    ) throws Exception {
        UserDTO user = this.signInService.execute(credentials);

        var usernameAndPassword = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        var auth = this.authenticationManager.authenticate(usernameAndPassword);

        var createdToken = this.createJWTTokenService.execute(user.getId().toString());

        var token = createdToken.getToken();
        var payload = createdToken.getPayload();

        var userCookie = this.createCookieService.execute(
                "user",
                payload.getUserId(),
                "api.crud.shop"
        );
        var authorizationCookie = this.createCookieService.execute(
                "authorization",
                token,
                "api.crud.shop"
        );

        response.addCookie(userCookie);
        response.addCookie(authorizationCookie);

        this.generateEventLog(
                user.getId(),
                200,
                "Usuário com id:" + String.valueOf(user.getId()) + " autenticado com sucesso!"
        );

        ResponseDTO responseDTO = this.responseMapper.toDTO(
                200,
                "Usuário com id: " + String.valueOf(user.getId()) + "autenticado com sucesso!",
                user
        );

        return ResponseEntity
                .status(200)
                .body(responseDTO);
    }
}
