package com.gabrielmira.clientegerenciamento.controllers;

import com.gabrielmira.clientegerenciamento.dtos.LoginDto;
import com.gabrielmira.clientegerenciamento.infra.TokenService;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.services.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

    final AuthenticationManager manager;
    final TokenService tokenService;

    public LoginController(UsuariosService usuariosService, AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<String> efetuarLogin(@RequestBody @Valid LoginDto dto) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.cpf(), dto.password());
        var authentication = manager.authenticate(authToken);
        String tokenJWT = tokenService.gerarToken((UsuariosModel) authentication.getPrincipal());

        return ResponseEntity.status(HttpStatus.OK).body(tokenJWT);
    }
}
