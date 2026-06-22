package com.superterminais.companyregistration.entity;

import com.superterminais.companyregistration.converter.BooleanSNConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "documentos")
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_arquivo", nullable = false, length = 255)
    private String nomeArquivo;

    @Column(name = "tipo_arquivo", nullable = false, length = 10)
    private String tipoArquivo;

    @Convert(converter = BooleanSNConverter.class)
    @Column(name = "obrigatorio", nullable = false, length = 1)
    private Boolean obrigatorio;

    @Column(name = "data_upload", nullable = false)
    private LocalDateTime dataUpload;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @PrePersist
    public void prePersist() {
        if (dataUpload == null) {
            dataUpload = LocalDateTime.now();
        }
    }
}