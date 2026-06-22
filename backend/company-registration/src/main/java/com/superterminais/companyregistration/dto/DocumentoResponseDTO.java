package com.superterminais.companyregistration.dto;

import java.time.LocalDateTime;

public record DocumentoResponseDTO(
        Long id,
        String nomeArquivo,
        String tipoArquivo,
        Boolean obrigatorio,
        LocalDateTime dataUpload,
        Long empresaId
) {
}