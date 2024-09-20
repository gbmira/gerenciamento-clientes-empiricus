package com.gabrielmira.clientegerenciamento.controllers;

import com.gabrielmira.clientegerenciamento.dtos.EmailsMsgRecordDto;
import com.gabrielmira.clientegerenciamento.services.EmailMsgService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailMensagemController {

    final EmailMsgService service;

    public EmailMensagemController(EmailMsgService service){
        this.service = service;
    }

    @PostMapping("/enviarEmail")
    public ResponseEntity<String> enviarEmail (@RequestBody @Valid EmailsMsgRecordDto dto){
        try{
            service.enviarEmail(dto);
            return ResponseEntity.status(HttpStatus.OK).body(dto.toString());
        }  catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar email: " + e.getMessage());
        }
    }
}
