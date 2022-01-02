package com.blogfrwk.apiblogfrwk.controller;

import com.blogfrwk.apiblogfrwk.dto.request.PhotoDTO;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.Photo;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeCreatedException;
import com.blogfrwk.apiblogfrwk.exception.PhotoCanNotBeDeletedException;
import com.blogfrwk.apiblogfrwk.exception.PhotoNotFoundException;
import com.blogfrwk.apiblogfrwk.exception.PostNotFoundException;
import com.blogfrwk.apiblogfrwk.service.PhotoService;
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
@RequestMapping("/api/photos")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PhotoController {

    private PhotoService photoService;

    @ApiOperation(value = "Cria uma nova Foto no album do Post")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na validação dos campos da Foto"),
            @ApiResponse(code = 401, message = "Falha de permissão: Não é possível criar Foto no Post de outro dono / Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping(consumes="application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createPhoto(@RequestBody @Valid PhotoDTO photoDTO) throws PhotoCanNotBeCreatedException, PostNotFoundException {
        return photoService.createPhoto(photoDTO);
    }

    @ApiOperation(value = "Busca todas Fotos existentes")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de Fotos retornada com sucesso"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @GetMapping
    public List<PhotoDTO> listAll() {
        return photoService.listAll();
    }

    @ApiOperation(value = "Busca uma Foto específica a partir de um ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto solicitado foi retornado com sucesso"),
            @ApiResponse(code = 401, message = "Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Foto com ID informado não foi encontrada / Erro no parâmetro da requisição Http"),
    })
    @GetMapping("/{id}")
    public PhotoDTO findByID(@PathVariable Long id) throws PhotoNotFoundException {
        return photoService.findByID(id);
    }

    @ApiOperation(value = "Deleta uma Foto existente")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Foto deletada com sucesso"),
            @ApiResponse(code = 401, message = "Falha de permissão: Não é possível excluir Foto no album de outro dono / Autenticação de usuário não realizada"),
            @ApiResponse(code = 404, message = "Comentário não encontrado para deleção / Erro no parâmetro da requisição Http"),
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteById(@PathVariable Long id) throws PhotoNotFoundException, PostNotFoundException, PhotoCanNotBeDeletedException {
        return photoService.deleteById(id);
    }
}
