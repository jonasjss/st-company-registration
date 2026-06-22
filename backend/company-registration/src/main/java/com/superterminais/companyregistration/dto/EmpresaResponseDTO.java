package com.superterminais.companyregistration.dto;

import com.superterminais.companyregistration.enums.StatusEmpresa;
import com.superterminais.companyregistration.enums.TipoPessoa;

import java.time.LocalDateTime;

public record EmpresaResponseDTO(
        Long id,
        TipoPessoa tipoPessoa,
        String razaoSocial,
        String nome,
        String nomeFantasia,
        String cnpj,
        String cpf,
        String identificadorEstrangeiro,
        Boolean faturamentoDireto,
        StatusEmpresa status,
        LocalDateTime dataCadastro,
        Long perfilId,
        String perfilNome,
        Long responsavelId,
        String responsavelNome
) {
}