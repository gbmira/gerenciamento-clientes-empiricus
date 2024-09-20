package com.gabrielmira.clientegerenciamento.services;

import com.gabrielmira.clientegerenciamento.dtos.UsuariosRecordDto;
import com.gabrielmira.clientegerenciamento.exceptions.CPFInvalidoException;
import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import com.gabrielmira.clientegerenciamento.repositories.UsuariosRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.stubbing.answers.ThrowsException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceTest {

    @InjectMocks
    private UsuariosService service;

    @Mock
    private UsuariosRepository repository;

    @Test
    public void deveRetornarUmaListaDeUsuarios(){
        UsuariosRecordDto usuario = new UsuariosRecordDto("Gabriel Mira", "49943884743", "bielteste", true);

        UsuariosModel model = new UsuariosModel();
        BeanUtils.copyProperties(usuario, model);
        Mockito.when(repository.findAll()).thenReturn(Collections.singletonList(model));
        List<UsuariosModel> usuarios = service.listarTodosUsuarios();
        System.out.println(usuarios);

        Assertions.assertEquals(1, usuarios.size());
    }

    @Test
    @DisplayName("Deve lançar exceção se o CPF tiver a quantidade menor que 11 números")
    public void deveLancarExcecaoQuandoCpfTiverMenos11Char(){
        UsuariosRecordDto usuario = new UsuariosRecordDto("Gabriel Mira", "4994388474", "bielteste", true);

        Assertions.assertThrows(CPFInvalidoException.class, () -> service.criar(usuario));


    }
}