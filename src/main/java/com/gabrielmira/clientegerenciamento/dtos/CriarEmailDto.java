package com.gabrielmira.clientegerenciamento.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CriarEmailDto(@NotBlank @Email String email, @NotBlank String cpf) {
}
