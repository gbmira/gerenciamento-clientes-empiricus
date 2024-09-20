package com.gabrielmira.clientegerenciamento.services;

import com.gabrielmira.clientegerenciamento.dtos.UsuariosRecordDto;
import com.gabrielmira.clientegerenciamento.exceptions.CPFInvalidoException;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.repositories.UsuariosRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UsuariosService {

    private final UsuariosRepository repository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.repository = usuariosRepository;
    }

    @Transactional
    public UsuariosModel criar(UsuariosRecordDto usuarioRecordDto) {
        if (usuarioRecordDto.cpf().length() < 11) {
            throw new CPFInvalidoException("O CPF está inválido, deve ter 11 números");
        }

        UsuariosModel usuariosModel = new UsuariosModel();
        BeanUtils.copyProperties(usuarioRecordDto, usuariosModel);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = passwordEncoder.encode(usuariosModel.getPassword());
        usuariosModel.setPassword(senhaCriptografada);

        return repository.save(usuariosModel);
    }

    public List<UsuariosModel> listarTodosUsuarios() {
        return repository.findAll();
    }

    public Optional<UsuariosModel> getUsuario(Long id) {
        return repository.findById(id);
    }

    public Optional<UsuariosModel> atualizarUsuario(Long id, UsuariosRecordDto dto) {
        Optional<UsuariosModel> optionalUsuario = repository.findById(id);
        if (optionalUsuario.isPresent()) {
            UsuariosModel usuariosModel = optionalUsuario.get();
            usuariosModel.setDataAtualizacao(LocalDateTime.now());
            BeanUtils.copyProperties(dto, usuariosModel);
            repository.save(usuariosModel);
        }
        return optionalUsuario;
    }

    public Optional<UsuariosModel> deletarUsuario(Long id) {
        Optional<UsuariosModel> optionalUsuario = repository.findById(id);
        if(optionalUsuario.isPresent()){
            repository.delete(optionalUsuario.get());
        }
        return optionalUsuario;
    }
}
