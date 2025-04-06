package com.joaov1ct0r.restful_api_users_java.modules.posts.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.UpdatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.UpdatePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/post/")
@Tag(name = "Post")
public class UpdatePostController extends BaseController {
    @Autowired
    private UpdatePostService updatePostService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @PutMapping("/")
    @Operation(summary = "Editar um post", description = "É possivel editar um post já cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post editado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Erro da requisição"),
            @ApiResponse(responseCode = "403", description = "Não permitido"),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @RequestBody  @Valid UpdatePostDTO updatePostDTO
            ) throws Exception {
        Object userIdAtt = req.getAttribute("userId");
        String userId = userIdAtt.toString();

        PostDTO updatedPost = this.updatePostService.execute(updatePostDTO, UUID.fromString(userId));

        this.generateEventLog(
                UUID.fromString(userId),
                204,
                "Post atualizado com sucesso!"
        );

        ResponseDTO response = this.responseMapper.toDTO(
                204,
                "Post updated with success!",
                updatedPost

        );

        return ResponseEntity.status(204).body(response);
    }
}
