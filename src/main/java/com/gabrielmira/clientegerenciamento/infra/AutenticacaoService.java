package com.gabrielmira.clientegerenciamento.infra;

import com.gabrielmira.clientegerenciamento.repositories.UsuariosRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService implements UserDetailsService {

    final UsuariosRepository repository;

    public AutenticacaoService(UsuariosRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUDCpf(username);
    }
}
