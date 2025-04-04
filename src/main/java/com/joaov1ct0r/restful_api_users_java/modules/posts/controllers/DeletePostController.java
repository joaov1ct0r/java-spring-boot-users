package com.joaov1ct0r.restful_api_users_java.modules.posts.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.DeletePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.DeletePostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.UUID;

@RestController
@RequestMapping("/post/")
@Tag(name = "Post")
public class DeletePostController extends BaseController {
    @Autowired
    private DeletePostService deletePostService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @DeleteMapping("/")
    @Operation(summary = "Deletar um post", description = "É possivel deletar um post já cadastrado")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Post deletado com sucesso!"),
            @ApiResponse(responseCode = "403", description = "Não permitido!"),
            @ApiResponse(responseCode = "500", description = "Erro interno!")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @Valid @RequestBody DeletePostDTO dto
            ) throws Exception {
        Object userIdAtt = req.getAttribute("userId");
        UUID userId = UUID.fromString(userIdAtt.toString());

        PostDTO deletedPost = this.deletePostService.execute(UUID.fromString(dto.getPostId()), userId);

        ResponseDTO response = this.responseMapper.toDTO(
                204,
                "Post deleted with success!",
                deletedPost
        );

        this.generateEventLog(
                userId,
                204,
                "Usuário com id: " + userId + " deletou o post com id: " + dto.getPostId()
        );

        return ResponseEntity.status(204).body(response);
    }
}
