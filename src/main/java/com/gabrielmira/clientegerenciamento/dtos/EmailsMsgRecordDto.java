package com.gabrielmira.clientegerenciamento.dtos;

import jakarta.validation.constraints.NotBlank;

public record EmailsMsgRecordDto(@NotBlank String destinatario,
                                 @NotBlank String assunto,
                                 @NotBlank String corpo) {
}
