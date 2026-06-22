package com.superterminais.companyregistration.service;

import com.superterminais.companyregistration.dto.DocumentoRequestDTO;
import com.superterminais.companyregistration.dto.DocumentoResponseDTO;
import com.superterminais.companyregistration.entity.Documento;
import com.superterminais.companyregistration.entity.Empresa;
import com.superterminais.companyregistration.exception.BusinessException;
import com.superterminais.companyregistration.exception.ResourceNotFoundException;
import com.superterminais.companyregistration.repository.DocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class DocumentoService {

    private static final Set<String> FORMATOS_PERMITIDOS = Set.of("PDF", "PNG", "JPG", "JPEG");

    private final DocumentoRepository documentoRepository;
    private final EmpresaService empresaService;

    @Transactional(readOnly = true)
    public List<DocumentoResponseDTO> listar() {
        return documentoRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public DocumentoResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public DocumentoResponseDTO criar(DocumentoRequestDTO dto) {
        Empresa empresa = empresaService.buscarEntidadePorId(dto.empresaId());
        validarDocumento(dto, null);
        Documento documento = Documento.builder()
                .nomeArquivo(dto.nomeArquivo())
                .tipoArquivo(normalizarTipoArquivo(dto.tipoArquivo()))
                .obrigatorio(dto.obrigatorio())
                .empresa(empresa)
                .build();
        return toResponse(documentoRepository.save(documento));
    }

    @Transactional
    public DocumentoResponseDTO atualizar(Long id, DocumentoRequestDTO dto) {
        Documento documento = buscarEntidadePorId(id);
        Empresa empresa = empresaService.buscarEntidadePorId(dto.empresaId());
        validarDocumento(dto, documento.getId());

        documento.setNomeArquivo(dto.nomeArquivo());
        documento.setTipoArquivo(normalizarTipoArquivo(dto.tipoArquivo()));
        documento.setObrigatorio(dto.obrigatorio());
        documento.setEmpresa(empresa);

        return toResponse(documento);
    }

    @Transactional
    public void excluir(Long id) {
        Documento documento = buscarEntidadePorId(id);
        documentoRepository.delete(documento);
    }

    private Documento buscarEntidadePorId(Long id) {
        return documentoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento não encontrado: " + id));
    }

    private void validarDocumento(DocumentoRequestDTO dto, Long idAtual) {
        validarFormatoArquivo(dto.tipoArquivo());

        boolean nomeJaExiste = idAtual == null
                ? documentoRepository.existsByEmpresaIdAndNomeArquivoIgnoreCase(dto.empresaId(), dto.nomeArquivo())
                : documentoRepository.existsByEmpresaIdAndNomeArquivoIgnoreCaseAndIdNot(dto.empresaId(), dto.nomeArquivo(), idAtual);

        if (nomeJaExiste) {
            throw new BusinessException("O arquivo está duplicado: " + dto.nomeArquivo());
        }
    }

    private void validarFormatoArquivo(String tipoArquivo) {
        if (!FORMATOS_PERMITIDOS.contains(normalizarTipoArquivo(tipoArquivo))) {
            throw new BusinessException("Formato de arquivo inválido. Permitidos: PDF, PNG, JPG e JPEG.");
        }
    }

    private String normalizarTipoArquivo(String tipoArquivo) {
        return tipoArquivo == null ? null : tipoArquivo.trim().toUpperCase(Locale.ROOT);
    }

    private DocumentoResponseDTO toResponse(Documento documento) {
        return new DocumentoResponseDTO(
                documento.getId(),
                documento.getNomeArquivo(),
                documento.getTipoArquivo(),
                documento.getObrigatorio(),
                documento.getDataUpload(),
                documento.getEmpresa().getId()
        );
    }
}