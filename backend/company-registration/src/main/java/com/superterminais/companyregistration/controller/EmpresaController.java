package com.superterminais.companyregistration.controller;

import com.superterminais.companyregistration.dto.AprovacaoEmpresaRequestDTO;
import com.superterminais.companyregistration.dto.EmpresaRequestDTO;
import com.superterminais.companyregistration.dto.EmpresaResponseDTO;
import com.superterminais.companyregistration.dto.ReprovacaoEmpresaRequestDTO;
import com.superterminais.companyregistration.service.EmpresaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empresas")
@RequiredArgsConstructor
public class EmpresaController {

    private final EmpresaService empresaService;

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDTO>> listar() {
        return ResponseEntity.ok(empresaService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empresaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<EmpresaResponseDTO> criar(@RequestBody @Valid EmpresaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(empresaService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EmpresaRequestDTO dto) {
        return ResponseEntity.ok(empresaService.atualizar(id, dto));
    }

    @PatchMapping("/{id}/aprovar")
    public ResponseEntity<EmpresaResponseDTO> aprovar(@PathVariable Long id, @RequestBody @Valid AprovacaoEmpresaRequestDTO dto) {
        return ResponseEntity.ok(empresaService.aprovar(id, dto.responsavelExternoId(), dto.usuarioInternoId()));
    }

    @PatchMapping("/{id}/reprovar")
    public ResponseEntity<EmpresaResponseDTO> reprovar(@PathVariable Long id, @RequestBody @Valid ReprovacaoEmpresaRequestDTO dto) {
        return ResponseEntity.ok(empresaService.reprovar(id, dto.usuarioInternoId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        empresaService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}