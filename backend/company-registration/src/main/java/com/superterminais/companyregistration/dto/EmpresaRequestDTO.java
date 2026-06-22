package com.superterminais.companyregistration.dto;

import com.superterminais.companyregistration.enums.StatusEmpresa;
import com.superterminais.companyregistration.enums.TipoPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EmpresaRequestDTO(
        @NotNull TipoPessoa tipoPessoa,
        @Size(min = 3, max = 200) String razaoSocial,
        @Size(min = 3, max = 200) String nome,
        @Size(max = 200) String nomeFantasia,
        @Size(max = 14) String cnpj,
        @Size(max = 11) String cpf,
        @Size(min = 3, max = 100) String identificadorEstrangeiro,
        Boolean faturamentoDireto,
        StatusEmpresa status,
        @NotNull(message = "Selecione um perfil para a empresa") Long perfilId,
        Long usuarioCadastroId,
        @NotNull(message = "Selecione um responsável para a empresa") Long responsavelId,
        List<@Valid DocumentoEmpresaRequestDTO> documentos
) {
}