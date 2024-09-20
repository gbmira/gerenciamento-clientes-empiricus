package com.gabrielmira.clientegerenciamento.services;

import com.gabrielmira.clientegerenciamento.dtos.CriarEmailDto;
import com.gabrielmira.clientegerenciamento.dtos.EmailsRecordDto;
import com.gabrielmira.clientegerenciamento.models.EmailsModel;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.repositories.EmailsRepository;
import com.gabrielmira.clientegerenciamento.repositories.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class EmailsService {

    private final EmailsRepository repository;
    private final UsuariosRepository usuariosRepository;
    private final EmailMsgService emailMsgService;

    public EmailsService(JavaMailSender javaMailSender, EmailsRepository emailsRepository, UsuariosRepository usuariosRepository, EmailMsgService emailMsgService) {
        this.repository = emailsRepository;
        this.usuariosRepository = usuariosRepository;
        this.emailMsgService = emailMsgService;
    }

    @Transactional
    public EmailsModel criar(EmailsModel emailsModel, CriarEmailDto dto) {
        Optional<UsuariosModel> usuarioOptional = usuariosRepository.findByCpf(dto.cpf());

        if (usuarioOptional.isPresent()) {
            BeanUtils.copyProperties(dto, emailsModel);
            emailsModel.setUsuario(usuarioOptional.get());

            String acao = "cadastrado";
            emailMsgService.enviarEmailNotificacao(dto.email(), dto.cpf(), acao);

            return repository.save(emailsModel);
        } else {
            throw new RuntimeException("Usuário não encontrado com CPF: " + dto.cpf());
        }
    }

    public List<EmailsModel> listarTodosEmails() {
        return repository.findAll();
    }

    public Optional<EmailsModel> getEmail(Long id) {
        return repository.findById(id);
    }

    public Optional<EmailsModel> atualizarEmail(Long id, EmailsRecordDto dto) {
        Optional<EmailsModel> optionalEmail = repository.findById(id);
        if (optionalEmail.isPresent()) {
            EmailsModel emailsModel = optionalEmail.get();
            emailsModel.setDataAtualizacao(LocalDateTime.now());
            BeanUtils.copyProperties(dto, emailsModel);
            repository.save(emailsModel);
        }
        return optionalEmail;
    }

    public Optional<EmailsModel> deletarEmail (Long id){
        Optional<EmailsModel> optionalEmail = repository.findById(id);
        if(optionalEmail.isPresent()){
            repository.delete(optionalEmail.get());

            EmailsModel emailsModel = optionalEmail.get();
            String acao = "deletado";
            emailMsgService.enviarEmailNotificacao(emailsModel.getEmail(), emailsModel.getUsuario().getCpf(), acao);
        }

        return optionalEmail;
    }
}
