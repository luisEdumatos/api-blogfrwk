package com.blogfrwk.apiblogfrwk.controller;

import com.blogfrwk.apiblogfrwk.dto.request.LoginRequest;
import com.blogfrwk.apiblogfrwk.dto.request.SignupRequest;
import com.blogfrwk.apiblogfrwk.service.AuthService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

   private AuthService authService;

    @ApiOperation(value = "Realiza login no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login efetuado com sucesso"),
            @ApiResponse(code = 401, message = "Falha no login, usuário não tem cadastro"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @ApiOperation(value = "Cadastra novo usuário de acesso ao sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Usuário ou e-mail já foram utilizados / Erro na validação dos campos"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return authService.registerUser(signUpRequest);
    }
}
