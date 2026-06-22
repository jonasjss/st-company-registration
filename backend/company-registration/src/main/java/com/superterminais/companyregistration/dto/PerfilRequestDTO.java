package com.superterminais.companyregistration.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PerfilRequestDTO(
        @NotBlank @Size(max = 100) String nome
) {
}