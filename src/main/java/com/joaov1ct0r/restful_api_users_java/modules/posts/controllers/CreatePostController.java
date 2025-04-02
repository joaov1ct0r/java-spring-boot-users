package com.joaov1ct0r.restful_api_users_java.modules.posts.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponseDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CreatePostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.CreatePostService;
import com.joaov1ct0r.restful_api_users_java.modules.domain.repositories.EventLogsRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/post/")
@Tag(name = "Post")
public class CreatePostController extends BaseController {
    @Autowired
    private CreatePostService createPostService;

    @Autowired
    private EventLogsRepository eventLogsRepository;

    @PostMapping("/")
    @Operation(summary = "Criar um novo post", description = "É possivel realizar a criação de um novo post")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Post criado com sucesso!", content = {
                    @Content(schema = @Schema(implementation = ResponseDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @RequestBody @Valid CreatePostDTO post
            ) throws Exception {
            Object userIdAtt = req.getAttribute("userId");
            UUID userId = UUID.fromString(userIdAtt.toString());

            PostDTO createdPost = this.createPostService.execute(post, userId);

            ResponseDTO response = this.responseMapper.toDTO(
                    201,
                    "Post created with success!",
                    createdPost
            );

            this.generateEventLog(
                    userId,
                    201,
                    "Usuário com id: " + userId + " criou um post com sucesso!"
            );

            return ResponseEntity.status(201).body(response);
    }
}
