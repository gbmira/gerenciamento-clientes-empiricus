package com.gabrielmira.clientegerenciamento.controllers;

import com.gabrielmira.clientegerenciamento.dtos.CriarEmailDto;
import com.gabrielmira.clientegerenciamento.dtos.EmailsRecordDto;
import com.gabrielmira.clientegerenciamento.models.EmailsModel;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.services.EmailsService;
import com.gabrielmira.clientegerenciamento.services.UsuariosService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/emails")
public class EmailsController {

    final EmailsService service;
    final UsuariosService usuariosService;

    public EmailsController(EmailsService service, UsuariosService usuariosService) {
        this.service = service;
        this.usuariosService = usuariosService;
    }

    @PostMapping
    public ResponseEntity<EmailsModel> criarEmail(@RequestBody @Valid CriarEmailDto dto) {
        EmailsModel emailsModel = new EmailsModel();
        BeanUtils.copyProperties(dto, emailsModel);

        EmailsModel createdEmail = service.criar(emailsModel, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmail);
    }

    @GetMapping("/usuarioId/{usuarioId}")
    public ResponseEntity<List<EmailsModel>> listarEmailsPorUsuarioId(@PathVariable Long usuarioId) {
        Optional<UsuariosModel> usuario = usuariosService.getUsuario(usuarioId);
        if (!usuario.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        List<EmailsModel> emails = usuario.get().getEmails();
        return ResponseEntity.ok(emails);
    }

    @GetMapping
    public ResponseEntity<List<EmailsModel>> listarTodosEmails() {
        List<EmailsModel> emailsList = service.listarTodosEmails();
        return ResponseEntity.status(HttpStatus.OK).body(emailsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmailPorEmailId(@PathVariable(value = "id") Long id) {
        Optional<EmailsModel> optionalEmail = service.getEmail(id);
        if (optionalEmail.isPresent()) {
            return ResponseEntity.ok(optionalEmail.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizarEmail(@PathVariable(value = "id") Long id, @RequestBody @Valid EmailsRecordDto dto) {
        Optional<EmailsModel> optionalEmail = service.atualizarEmail(id, dto);
        if (optionalEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(optionalEmail.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarEmail(@PathVariable(value = "id") Long id) {
        Optional<EmailsModel> optionalEmail = service.deletarEmail(id);
        if (optionalEmail.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Email deletado com sucesso");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email não encontrado");
    }
}
