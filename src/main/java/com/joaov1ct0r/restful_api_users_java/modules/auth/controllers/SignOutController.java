package com.joaov1ct0r.restful_api_users_java.modules.auth.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
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
public class SignOutController extends BaseController {
    @GetMapping("/")
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
                "Sign out realizado com sucesso",
                null
        );

        return ResponseEntity.status(200).body(responseDTO);
    }

}
