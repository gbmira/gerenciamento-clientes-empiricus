package com.gabrielmira.clientegerenciamento.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsuariosRecordDto(@NotBlank String nome,
                                @NotBlank String cpf,
                                @NotBlank
                                @Size(min = 6, max = 10, message = "A senha deve ter entre 6 - 10 caracteres") String password,
                                @NotNull boolean ehAdmin) {
}
