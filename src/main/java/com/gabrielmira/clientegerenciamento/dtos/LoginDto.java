package com.gabrielmira.clientegerenciamento.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(@NotBlank String cpf, @NotBlank String password) {
}
