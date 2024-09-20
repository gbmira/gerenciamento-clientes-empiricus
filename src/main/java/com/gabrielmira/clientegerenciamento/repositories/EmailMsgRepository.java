package com.gabrielmira.clientegerenciamento.repositories;

import com.gabrielmira.clientegerenciamento.models.EmailMsgModel;
import org.springframework.data.jpa.repository.JpaRepository;



public interface EmailMsgRepository extends JpaRepository<EmailMsgModel, Long> {
}
