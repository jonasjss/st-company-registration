package com.superterminais.companyregistration.controller;

import com.superterminais.companyregistration.dto.PerfilRequestDTO;
import com.superterminais.companyregistration.dto.PerfilResponseDTO;
import com.superterminais.companyregistration.service.PerfilService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<PerfilResponseDTO>> listar() {
        return ResponseEntity.ok(perfilService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(perfilService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PerfilResponseDTO> criar(@RequestBody @Valid PerfilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PerfilResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid PerfilRequestDTO dto) {
        return ResponseEntity.ok(perfilService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        perfilService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}