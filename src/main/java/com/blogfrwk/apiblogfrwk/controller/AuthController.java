package com.blogfrwk.apiblogfrwk.controller;

import com.blogfrwk.apiblogfrwk.dto.request.LoginRequest;
import com.blogfrwk.apiblogfrwk.dto.request.SignupRequest;
import com.blogfrwk.apiblogfrwk.dto.response.JwtResponse;
import com.blogfrwk.apiblogfrwk.dto.response.MessageResponse;
import com.blogfrwk.apiblogfrwk.entity.UserSystem;
import com.blogfrwk.apiblogfrwk.repository.UserSystemRepository;
import com.blogfrwk.apiblogfrwk.security.jwt.JwtUtils;
import com.blogfrwk.apiblogfrwk.security.services.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserSystemRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @ApiOperation(value = "Realiza login no sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login efetuado com sucesso"),
            @ApiResponse(code = 401, message = "Falha no login, usuário não tem cadastro"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }

    @ApiOperation(value = "Cadastra novo usuário de acesso ao sistema")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Usuário cadastrado com sucesso"),
            @ApiResponse(code = 400, message = "Usuário ou e-mail já foram utilizados / Erro na validação dos campos"),
            @ApiResponse(code = 404, message = "Erro no parâmetro da requisição Http"),
    })
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        UserSystem user = new UserSystem(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));


        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
