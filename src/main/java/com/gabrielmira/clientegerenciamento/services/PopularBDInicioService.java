package com.gabrielmira.clientegerenciamento.services;

import com.gabrielmira.clientegerenciamento.dtos.UsuariosRecordDto;
import com.gabrielmira.clientegerenciamento.exceptions.CPFInvalidoException;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.repositories.PopularBDInicioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PopularBDInicioService {

    final PopularBDInicioRepository repository;

    public PopularBDInicioService(PopularBDInicioRepository repository){
        this.repository = repository;
    }

    @Transactional
    public UsuariosModel criar() {

        UsuariosRecordDto dto = new UsuariosRecordDto("admin", "85575443745", "admin123", true);

        if (dto.cpf().length() < 11) {
            throw new CPFInvalidoException("O CPF está inválido, deve ter 11 números");
        }

        UsuariosModel usuariosModel = new UsuariosModel();
        BeanUtils.copyProperties(dto, usuariosModel);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String senhaCriptografada = passwordEncoder.encode(usuariosModel.getPassword());
        usuariosModel.setPassword(senhaCriptografada);

        return repository.save(usuariosModel);
    }
}
