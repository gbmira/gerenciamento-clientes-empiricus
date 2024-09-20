package com.gabrielmira.clientegerenciamento.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailsRecordDto(@NotBlank @Email String email) {
}
