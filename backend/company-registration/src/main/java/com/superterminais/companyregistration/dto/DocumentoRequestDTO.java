package com.superterminais.companyregistration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record DocumentoRequestDTO(
        @NotBlank @Size(max = 255) String nomeArquivo,
        @NotBlank @Size(max = 10) String tipoArquivo,
        @NotNull Boolean obrigatorio,
        @NotNull Long empresaId
) {
}