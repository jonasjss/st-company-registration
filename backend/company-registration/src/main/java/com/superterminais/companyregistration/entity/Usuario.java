package com.superterminais.companyregistration.entity;

import com.superterminais.companyregistration.enums.TipoUsuario;
import com.superterminais.companyregistration.enums.PermissaoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @Column(name = "email", nullable = false, unique = true, length = 200)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private TipoUsuario tipoUsuario;

    @Builder.Default
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "usuario_permissoes", joinColumns = @JoinColumn(name = "usuario_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "permissao", nullable = false, length = 50)
    private Set<PermissaoUsuario> permissoes = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "responsavel")
    private List<Empresa> empresas = new ArrayList<>();
}