package com.gabrielmira.clientegerenciamento.controllers;

import com.gabrielmira.clientegerenciamento.dtos.UsuariosRecordDto;
import com.gabrielmira.clientegerenciamento.infra.TokenService;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.services.PopularBDInicioService;
import com.gabrielmira.clientegerenciamento.services.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/popularBD")
public class PopularBDInicioController {

    final PopularBDInicioService service;

    public PopularBDInicioController(PopularBDInicioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<UsuariosModel> criarUsuario() {
        UsuariosModel novoUsuario = service.criar();
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }
}
