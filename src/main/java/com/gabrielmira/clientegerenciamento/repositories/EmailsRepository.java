package com.gabrielmira.clientegerenciamento.repositories;

import com.gabrielmira.clientegerenciamento.models.EmailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmailsRepository extends JpaRepository<EmailsModel, Long> {
}
