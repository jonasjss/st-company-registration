package com.superterminais.companyregistration.dto;

import jakarta.validation.constraints.NotNull;

public record ReprovacaoEmpresaRequestDTO(
        @NotNull Long usuarioInternoId
) {
}