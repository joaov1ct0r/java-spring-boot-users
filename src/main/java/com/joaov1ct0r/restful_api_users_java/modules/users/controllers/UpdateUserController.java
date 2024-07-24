package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UpdateUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.UpdateUserService;
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
@RequestMapping("/user/")
public class UpdateUserController extends BaseController {
    @Autowired
    private UpdateUserService updateUserService;

    @PutMapping("/")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @Valid @RequestBody UpdateUserDTO userDTO
            ) {
        Object userIdAtt = req.getAttribute("userId");
        String tokenUserId = userIdAtt.toString();

        var updatedUser = this.updateUserService.execute(userDTO, tokenUserId);

        this.generateEventLog(
                UUID.fromString(tokenUserId),
                204,
                "Usuário atualizado com sucesso!"
        );

        ResponseDTO response = this.responseMapper.toDTO(
                204,
                "Usuário atualizado com sucesso!",
                updatedUser
        );

        return ResponseEntity.status(204).body(response);
    }
}
