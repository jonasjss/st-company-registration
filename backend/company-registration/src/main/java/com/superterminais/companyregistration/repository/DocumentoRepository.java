package com.superterminais.companyregistration.repository;

import com.superterminais.companyregistration.entity.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, Long> {
    boolean existsByEmpresaIdAndNomeArquivoIgnoreCase(Long empresaId, String nomeArquivo);

    boolean existsByEmpresaIdAndNomeArquivoIgnoreCaseAndIdNot(Long empresaId, String nomeArquivo, Long id);
}