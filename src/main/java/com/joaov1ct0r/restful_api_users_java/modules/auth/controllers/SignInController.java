package com.joaov1ct0r.restful_api_users_java.modules.auth.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.auth.dtos.SignInDTO;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.CreateCookieService;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.CreateJWTTokenService;
import com.joaov1ct0r.restful_api_users_java.modules.auth.services.SignInService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin/")
public class SignInController extends BaseController {
    @Autowired
    private SignInService signInService;

    @Autowired
    private CreateJWTTokenService createJWTTokenService;

    @Autowired
    private CreateCookieService createCookieService;

    @PostMapping("/")
    public ResponseEntity<Object> handle(
            HttpServletResponse response,
            @Valid @RequestBody SignInDTO credentials
    ) throws Exception {
        UserDTO user = this.signInService.execute(credentials);

        var createdToken = this.createJWTTokenService.execute(user.getId().toString());

        var token = createdToken.getToken();
        var payload = createdToken.getPayload();

        var userCookie = this.createCookieService.execute("user", String.valueOf(payload),"localhost");
        var authorizationCookie = this.createCookieService.execute("authorization", token, "localhost");

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
