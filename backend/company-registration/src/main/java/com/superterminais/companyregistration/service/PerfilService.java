package com.superterminais.companyregistration.service;

import com.superterminais.companyregistration.dto.PerfilRequestDTO;
import com.superterminais.companyregistration.dto.PerfilResponseDTO;
import com.superterminais.companyregistration.entity.Perfil;
import com.superterminais.companyregistration.exception.ResourceNotFoundException;
import com.superterminais.companyregistration.repository.PerfilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerfilService {

    private final PerfilRepository perfilRepository;

    @Transactional(readOnly = true)
    public List<PerfilResponseDTO> listar() {
        return perfilRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PerfilResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public PerfilResponseDTO criar(PerfilRequestDTO dto) {
        Perfil perfil = Perfil.builder().nome(dto.nome()).build();
        return toResponse(perfilRepository.save(perfil));
    }

    @Transactional
    public PerfilResponseDTO atualizar(Long id, PerfilRequestDTO dto) {
        Perfil perfil = buscarEntidadePorId(id);
        perfil.setNome(dto.nome());
        return toResponse(perfil);
    }

    @Transactional
    public void excluir(Long id) {
        Perfil perfil = buscarEntidadePorId(id);
        perfilRepository.delete(perfil);
    }

    public Perfil buscarEntidadePorId(Long id) {
        return perfilRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil não encontrado: " + id));
    }

    private PerfilResponseDTO toResponse(Perfil perfil) {
        return new PerfilResponseDTO(perfil.getId(), perfil.getNome());
    }
}