package com.gabrielmira.clientegerenciamento.controllers;

import com.gabrielmira.clientegerenciamento.dtos.UsuariosRecordDto;
import com.gabrielmira.clientegerenciamento.infra.TokenService;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.services.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuariosController {

    final UsuariosService service;
    final AuthenticationManager manager;
    final TokenService tokenService;

    public UsuariosController(UsuariosService usuariosService, AuthenticationManager manager, TokenService tokenService) {
        this.service = usuariosService;
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<UsuariosModel> criarUsuario(@RequestBody @Valid UsuariosRecordDto usuarioRecordDto) {
        UsuariosModel novoUsuario = service.criar(usuarioRecordDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @GetMapping
    public ResponseEntity<List<UsuariosModel>> listarTodosUsuarios() {
        List<UsuariosModel> usuariosList = service.listarTodosUsuarios();
        return ResponseEntity.ok(usuariosList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> consultarUsuarioPorId(@PathVariable(value = "id") Long id) {
        Optional<UsuariosModel> optionalUsuario = service.getUsuario(id);
        if (optionalUsuario.isPresent()) {
            return ResponseEntity.ok(optionalUsuario.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarUsuario(@PathVariable(value = "id") Long id, @RequestBody @Valid UsuariosRecordDto dto) {
        Optional<UsuariosModel> optionalUsuario = service.atualizarUsuario(id, dto);
        if (optionalUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalUsuario.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarUsuario(@PathVariable(value = "id") Long id) {
        Optional<UsuariosModel> optionalUsuario = service.deletarUsuario(id);
        if (optionalUsuario.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
    }
}
