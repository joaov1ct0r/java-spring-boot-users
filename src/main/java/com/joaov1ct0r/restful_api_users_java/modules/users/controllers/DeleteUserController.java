package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.DeleteUserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.UserDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.DeleteUserService;
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
public class DeleteUserController extends BaseController {
    @Autowired
    private DeleteUserService deleteUserService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @DeleteMapping("/")
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
                "Usuário deletado com sucesso!",
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
