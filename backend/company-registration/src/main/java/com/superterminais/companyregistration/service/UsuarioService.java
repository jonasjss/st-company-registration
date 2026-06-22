package com.superterminais.companyregistration.service;

import com.superterminais.companyregistration.dto.UsuarioRequestDTO;
import com.superterminais.companyregistration.dto.UsuarioResponseDTO;
import com.superterminais.companyregistration.entity.Usuario;
import com.superterminais.companyregistration.enums.PermissaoUsuario;
import com.superterminais.companyregistration.enums.TipoUsuario;
import com.superterminais.companyregistration.exception.ResourceNotFoundException;
import com.superterminais.companyregistration.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public UsuarioResponseDTO criar(UsuarioRequestDTO dto) {
        Usuario usuario = Usuario.builder()
                .nome(dto.nome())
                .email(dto.email())
                .tipoUsuario(dto.tipoUsuario())
                .permissoes(definirPermissoes(dto.tipoUsuario(), dto.permissoes()))
                .build();
        return toResponse(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDTO atualizar(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = buscarEntidadePorId(id);
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setTipoUsuario(dto.tipoUsuario());
        usuario.setPermissoes(definirPermissoes(dto.tipoUsuario(), dto.permissoes()));
        return toResponse(usuario);
    }

    @Transactional
    public void excluir(Long id) {
        Usuario usuario = buscarEntidadePorId(id);
        usuarioRepository.delete(usuario);
    }

    public Usuario buscarEntidadePorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado: " + id));
    }

    private Set<PermissaoUsuario> definirPermissoes(TipoUsuario tipoUsuario, Set<PermissaoUsuario> permissoesInformadas) {
        if (permissoesInformadas != null && !permissoesInformadas.isEmpty()) {
            return permissoesInformadas;
        }

        if (tipoUsuario == TipoUsuario.EXTERNO) {
            return EnumSet.of(PermissaoUsuario.EMPRESA_CADASTRO);
        }

        return EnumSet.of(
                PermissaoUsuario.EMPRESA_MASTER,
                PermissaoUsuario.EMPRESA_LISTA,
                PermissaoUsuario.EMPRESA_EDICAO
        );
    }

    private UsuarioResponseDTO toResponse(Usuario usuario) {
        return new UsuarioResponseDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipoUsuario(), usuario.getPermissoes());
    }
}