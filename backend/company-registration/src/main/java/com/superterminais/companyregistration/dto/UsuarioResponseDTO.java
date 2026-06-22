package com.superterminais.companyregistration.dto;

import com.superterminais.companyregistration.enums.TipoUsuario;
import com.superterminais.companyregistration.enums.PermissaoUsuario;

import java.util.Set;

public record UsuarioResponseDTO(Long id, String nome, String email, TipoUsuario tipoUsuario, Set<PermissaoUsuario> permissoes) {
}