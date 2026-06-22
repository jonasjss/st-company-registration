package com.superterminais.companyregistration.controller;

import com.superterminais.companyregistration.dto.DocumentoRequestDTO;
import com.superterminais.companyregistration.dto.DocumentoResponseDTO;
import com.superterminais.companyregistration.service.DocumentoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documentos")
@RequiredArgsConstructor
public class DocumentoController {

    private final DocumentoService documentoService;

    @GetMapping
    public ResponseEntity<List<DocumentoResponseDTO>> listar() {
        return ResponseEntity.ok(documentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(documentoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<DocumentoResponseDTO> criar(@RequestBody @Valid DocumentoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentoService.criar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentoResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid DocumentoRequestDTO dto) {
        return ResponseEntity.ok(documentoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        documentoService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}