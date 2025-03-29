package com.joaov1ct0r.restful_api_users_java.modules.posts.controllers;

import com.joaov1ct0r.restful_api_users_java.modules.domain.controllers.BaseController;
import com.joaov1ct0r.restful_api_users_java.modules.domain.dtos.ResponsePagDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.CountAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.FindAllPostsDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.dtos.PostDTO;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.CountAllPostsService;
import com.joaov1ct0r.restful_api_users_java.modules.posts.services.FindAllPostsService;
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
import java.util.List;
import java.util.UUID;
import static java.lang.Math.toIntExact;

@RestController
@RequestMapping("/post/")
@Tag(name = "Post")
public class FindAllPostsController extends BaseController {
    @Autowired
    private FindAllPostsService findAllPostsService;

    @Autowired
    private CountAllPostsService countAllPostsService;

    @GetMapping("/")
    @Operation(summary = "Buscar por todos os posts", description = "É possivel realizar uma busca por todos os posts com ou sem filtro de (content)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Busca por posts realizada com sucesso!", content = {
                    @Content(schema = @Schema(implementation = ResponsePagDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro Interno")
    })
    @SecurityRequirement(name = "jwt_auth")
    public ResponseEntity<Object> handle(
            HttpServletRequest req,
            @RequestParam(name = "perPage", defaultValue = "20") String perPage,
            @RequestParam(name = "page", defaultValue = "1") String page,
            @RequestParam(name = "content", defaultValue = "null") String content
    ) {
        FindAllPostsDTO findAllPostsDTO = new FindAllPostsDTO(
                Integer.parseInt(perPage),
                Integer.parseInt(page),
                content.equals("null") ? null : content
        );
        List<PostDTO> posts = this.findAllPostsService.execute(findAllPostsDTO);

        CountAllPostsDTO countAllPostsDTO = new CountAllPostsDTO(
                Integer.parseInt(perPage),
                Integer.parseInt(page),
                content.equals("null") ? null : content
        );

        long total = this.countAllPostsService.execute(countAllPostsDTO);

        Object userIdAtt = req.getAttribute("userId");
        String userId = userIdAtt.toString();

        this.generateEventLog(
                UUID.fromString(userId),
                200,
                "Usuário com id: " + userId + " Buscou todos os posts com sucesso!"
        );

        ResponsePagDTO response = this.responseMapper.toPagDTO(
                200,
                "Post(s) found with success",
                posts,
                findAllPostsDTO.getPage() - 1,
                findAllPostsDTO.getPage() + 1,
                toIntExact(total)
        );

        return ResponseEntity.status(200).body(response);
    }
}
