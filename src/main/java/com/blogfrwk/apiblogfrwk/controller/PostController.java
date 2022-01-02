package com.blogfrwk.apiblogfrwk.controller;


import com.blogfrwk.apiblogfrwk.dto.request.PostDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.exception.PostCanNotBeDeletedException;
import com.blogfrwk.apiblogfrwk.exception.PostCanNotBeUpdatedException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

    private PostService postService;

    @ApiOperation(value = "Cria novo Post")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Novo Post cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos do Post"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createPost(@RequestBody @Valid PostDTO postDTO) {
        return postService.createPost(postDTO);
    }

    @ApiOperation(value = "Busca todos Posts existentes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Posts retornada com sucesso"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @GetMapping
    public List<PostDTO> listAll() {
        return postService.listAll();
    }

    @ApiOperation(value = "Busca um Post específico a partir de um ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post solicitado foi retornado com sucesso"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Post com ID informado não foi encontrado / Erro no parâmetro da requisição Http"),
    })
    @GetMapping("/{id}")
    public PostDTO findById(@PathVariable Long id) throws PostNotFoundException {
        return postService.findByID(id);
    }

    @ApiOperation(value = "Atualiza um Post existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post atualizado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos do Post"),
            @ApiResponse(code = 401, message = "Falha de permissão: Não é possível atualizar Post de outro dono / Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Post não encontrado para atualização / Erro no parâmetro da requisição Http"),
    })
    @PutMapping("/{id}")
    public MessageResponse updateById(@PathVariable Long id, @RequestBody @Valid PostDTO postDTO) throws PostNotFoundException, PostCanNotBeUpdatedException {
        return postService.updateById(id, postDTO);
    }

    @ApiOperation(value = "Deleta um Post existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post deletado com sucesso"),
            @ApiResponse(code = 401, message = "Falha de permissão: Não é possível excluir Post de outro dono / Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Post não encontrado para deleção / Erro no parâmetro da requisição Http"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteById(@PathVariable Long id) throws PostNotFoundException, PostCanNotBeDeletedException {
        return postService.deleteById(id);
    }
}
