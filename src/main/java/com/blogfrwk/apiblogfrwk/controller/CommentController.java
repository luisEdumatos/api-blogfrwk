package com.blogfrwk.apiblogfrwk.controller;

import com.blogfrwk.apiblogfrwk.dto.request.CommentDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.service.CommentService;
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
@RequestMapping("/api/comments")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CommentController {

    private CommentService commentService;

    @ApiOperation(value = "Cria um novo comentário no Post")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Comentario criado com sucesso"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos informados"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createEmployee(@RequestBody @Valid CommentDTO commentDTO) {
        return commentService.createComment(commentDTO);
    }

    @ApiOperation(value = "Busca todos comentários existentes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de comentários retornada com sucesso"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @GetMapping
    public List<CommentDTO> listAll() {
        return commentService.listAll();
    }
}
