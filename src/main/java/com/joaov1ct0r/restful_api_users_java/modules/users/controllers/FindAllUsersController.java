package com.joaov1ct0r.restful_api_users_java.modules.users.controllers;

import static java.lang.Math.toIntExact;
import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponsePagDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.CountAllUsersDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.dtos.FindAllUsersDTO;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.CountAllUsersService;
import com.joaov1ct0r.restful_api_users_java.modules.users.services.FindAllUsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user/")
@Tag(name = "Usuário")
public class FindAllUsersController extends BaseController {
    @Autowired
    private FindAllUsersService findAllUsersService;

    @Autowired
    private CountAllUsersService countAllUsersService;

    @GetMapping("/")
    @Operation(summary = "Buscar por todos o usuários", description = "É possivel realizar uma busca por todos usuários " +
            "com ou sem filtros de (name, username, email)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca por usuários realizada com sucesso!", content = {
                    @Content(schema = @Schema(implementation = ResponsePagDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @RequestParam(name = "perPage", defaultValue = "20") String perPage,
            @RequestParam(name = "page", defaultValue = "1") String page,
            @RequestParam(name = "name", defaultValue = "null") String name,
            @RequestParam(name = "username", defaultValue = "null") String username,
            @RequestParam(name = "email", defaultValue = "null") String email
    ) {
        var query = new FindAllUsersDTO(
                Integer.parseInt(perPage),
                Integer.parseInt(page),
                name.equals("null") ? null : name,
                username.equals("null") ? null : username,
                email.equals("null") ? null : email
        );
        var users = this.findAllUsersService.execute(query);
        var countAllUsersDTO = new CountAllUsersDTO(
                query.getPerPage(),
                query.getPage(),
                query.getName(),
                query.getUsername(),
                query.getEmail()
        );
        var usersCount = this.countAllUsersService.execute(countAllUsersDTO);

        Object userIdAtt = req.getAttribute("userId");
        String userId = userIdAtt.toString();

        this.generateEventLog(
                UUID.fromString(userId),
                200,
                "Usuário com id: " + userId + " Buscou todos os usuários com sucesso!"
        );

        ResponsePagDTO response = this.responseMapper.toPagDTO(
                200,
                "Usuário com id: " + userId + " Buscou todos os usuários com sucesso!",
                users,
                query.getPage() - 1,
                query.getPage() + 1,
                toIntExact(usersCount)
        );

        return ResponseEntity.status(200).body(response);
    }
}
