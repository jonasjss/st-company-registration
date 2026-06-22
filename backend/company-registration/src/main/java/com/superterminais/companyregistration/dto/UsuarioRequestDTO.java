package com.superterminais.companyregistration.dto;

import com.superterminais.companyregistration.enums.TipoUsuario;
import com.superterminais.companyregistration.enums.PermissaoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record UsuarioRequestDTO(
        @NotBlank @Size(max = 150) String nome,
        @NotBlank @Email @Size(max = 150) String email,
        @NotNull TipoUsuario tipoUsuario,
        Set<PermissaoUsuario> permissoes
) {
}