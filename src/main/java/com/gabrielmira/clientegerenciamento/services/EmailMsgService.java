package com.gabrielmira.clientegerenciamento.services;

import com.gabrielmira.clientegerenciamento.dtos.EmailsMsgRecordDto;
import com.gabrielmira.clientegerenciamento.models.EmailMsgModel;
import com.gabrielmira.clientegerenciamento.models.EmailsModel;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.repositories.EmailMsgRepository;
import com.gabrielmira.clientegerenciamento.repositories.UsuariosRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailMsgService {

    final JavaMailSender javaMailSender;
    final EmailMsgRepository repository;
    final UsuariosRepository usuariosRepository;

    public EmailMsgService(JavaMailSender javaMailSender, EmailMsgRepository repository, UsuariosRepository usuariosRepository) {
        this.javaMailSender = javaMailSender;
        this.repository = repository;
        this.usuariosRepository = usuariosRepository;
    }

    @Async
    public void enviarEmail(EmailsMsgRecordDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(dto.destinatario());
        message.setSubject(dto.assunto());
        message.setText(dto.corpo());
        javaMailSender.send(message);

        EmailMsgModel email = new EmailMsgModel();
        BeanUtils.copyProperties(dto, email);
        email.setDataEnvio(LocalDateTime.now());
        repository.save(email);
    }

    public void enviarEmailNotificacao(String email, String cpf, String acao) {
        List<UsuariosModel> listEhAdmin = usuariosRepository.findByEhAdminTrue();
        for (UsuariosModel admin : listEhAdmin) {
            // Verifique se a lista de emails do administrador não está vazia
            if (admin.getEmails() != null && !admin.getEmails().isEmpty()) {
                for (EmailsModel emailModel : admin.getEmails()) {
                    EmailsMsgRecordDto emailDto = new EmailsMsgRecordDto(
                            emailModel.getEmail(), // Obtendo o email da lista
                            "O email " + email + " foi " + acao + " para o usuário de CPF " + cpf,
                            "O email " + email + " foi " + acao + " para o usuário de CPF " + cpf
                    );

                    // Enviar email usando o EmailMensagemService
                    enviarEmail(emailDto);
                }
            }
        }
    }
}
