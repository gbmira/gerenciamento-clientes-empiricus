package com.gabrielmira.clientegerenciamento.repositories;

import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuariosRepository extends JpaRepository<UsuariosModel, Long> {
    Optional<UsuariosModel> findByCpf(String cpf);

    @Query("SELECT u FROM UsuariosModel u WHERE u.cpf = :cpf")
    UserDetails findByUDCpf(String cpf);

    List<UsuariosModel> findByEhAdminTrue();
};

