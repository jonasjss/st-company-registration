package com.superterminais.companyregistration.entity;

import com.superterminais.companyregistration.converter.BooleanSNConverter;
import com.superterminais.companyregistration.enums.StatusEmpresa;
import com.superterminais.companyregistration.enums.TipoPessoa;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empresas")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_pessoa", nullable = false, length = 20)
    private TipoPessoa tipoPessoa;

    @Column(name = "razao_social", length = 200)
    private String razaoSocial;

    @Column(name = "nome", length = 200)
    private String nome;

    @Column(name = "nome_fantasia", length = 200)
    private String nomeFantasia;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "identificador_estrangeiro", length = 100)
    private String identificadorEstrangeiro;

    @Convert(converter = BooleanSNConverter.class)
    @Column(name = "faturamento_direto", nullable = false, length = 1)
    private Boolean faturamentoDireto;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private StatusEmpresa status;

    @Column(name = "data_cadastro", nullable = false, updatable = false)
    private LocalDateTime dataCadastro;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id", nullable = false)
    private Perfil perfil;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "responsavel_id", nullable = false)
    private Usuario responsavel;

    @Builder.Default
    @OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Documento> documentos = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        if (dataCadastro == null) {
            dataCadastro = LocalDateTime.now();
        }
        if (status == null) {
            status = StatusEmpresa.PENDENTE;
        }
        if (faturamentoDireto == null) {
            faturamentoDireto = false;
        }
    }
}