package com.gabrielmira.clientegerenciamento.repositories;

import com.gabrielmira.clientegerenciamento.models.UsuariosModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PopularBDInicioRepository extends JpaRepository<UsuariosModel, Long> {
}
