package com.superterminais.companyregistration.dto;

import jakarta.validation.constraints.NotNull;

public record AprovacaoEmpresaRequestDTO(
        @NotNull Long responsavelExternoId,
        @NotNull Long usuarioInternoId
) {
}