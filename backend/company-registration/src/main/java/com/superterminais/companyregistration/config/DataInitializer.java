package com.superterminais.companyregistration.config;

import com.superterminais.companyregistration.entity.Documento;
import com.superterminais.companyregistration.entity.Empresa;
import com.superterminais.companyregistration.entity.Perfil;
import com.superterminais.companyregistration.entity.Usuario;
import com.superterminais.companyregistration.enums.PermissaoUsuario;
import com.superterminais.companyregistration.enums.StatusEmpresa;
import com.superterminais.companyregistration.enums.TipoPessoa;
import com.superterminais.companyregistration.enums.TipoUsuario;
import com.superterminais.companyregistration.repository.EmpresaRepository;
import com.superterminais.companyregistration.repository.PerfilRepository;
import com.superterminais.companyregistration.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumSet;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaRepository empresaRepository;

    @Bean
    CommandLineRunner carregarDadosDemonstrativos() {
        return args -> {
            if (empresaRepository.count() > 0 || usuarioRepository.count() > 0 || perfilRepository.count() > 0) {
                return;
            }

            Perfil despachante = perfilRepository.save(Perfil.builder().nome("Despachante").build());
            Perfil beneficiario = perfilRepository.save(Perfil.builder().nome("Beneficiário").build());
            Perfil consignatario = perfilRepository.save(Perfil.builder().nome("Consignatário").build());

            Usuario usuarioExterno = usuarioRepository.save(Usuario.builder()
                    .nome("Marina Souza")
                    .email("marina.souza@example.com")
                    .tipoUsuario(TipoUsuario.EXTERNO)
                    .permissoes(EnumSet.of(PermissaoUsuario.EMPRESA_CADASTRO))
                    .build());

            Usuario usuarioInterno = usuarioRepository.save(Usuario.builder()
                    .nome("Carlos Andrade")
                    .email("carlos.andrade@example.com")
                    .tipoUsuario(TipoUsuario.INTERNO)
                    .permissoes(EnumSet.of(
                            PermissaoUsuario.EMPRESA_MASTER,
                            PermissaoUsuario.EMPRESA_LISTA,
                            PermissaoUsuario.EMPRESA_EDICAO
                    ))
                    .build());

            salvarEmpresaDemonstrativa(
                    "Norte Tech",
                    "Norte Tech Soluções Digitais LTDA",
                    "11222333000181",
                    despachante,
                    usuarioExterno,
                    StatusEmpresa.APROVADA,
                    true
            );

            salvarEmpresaDemonstrativa(
                    "Amazônia Log",
                    "Amazônia Logística Portuária LTDA",
                    "19131243000197",
                    beneficiario,
                    usuarioExterno,
                    StatusEmpresa.PENDENTE,
                    false
            );

            salvarEmpresaDemonstrativa(
                    "Rio Negro Trading",
                    "Rio Negro Trading Importação e Exportação LTDA",
                    "04153748000185",
                    consignatario,
                    usuarioExterno,
                    StatusEmpresa.REPROVADA,
                    false
            );

            salvarPessoaFisicaDemonstrativa(usuarioExterno, despachante);
            salvarPessoaEstrangeiraDemonstrativa(usuarioExterno, beneficiario);
        };
    }

    private void salvarEmpresaDemonstrativa(
            String nomeFantasia,
            String razaoSocial,
            String cnpj,
            Perfil perfil,
            Usuario responsavel,
            StatusEmpresa status,
            boolean faturamentoDireto
    ) {
        Empresa empresa = Empresa.builder()
                .tipoPessoa(TipoPessoa.JURIDICA)
                .razaoSocial(razaoSocial)
                .nomeFantasia(nomeFantasia)
                .cnpj(cnpj)
                .faturamentoDireto(faturamentoDireto)
                .status(status)
                .perfil(perfil)
                .responsavel(responsavel)
                .build();

        adicionarDocumentoObrigatorio(empresa, "documento-" + nomeFantasia.toLowerCase().replaceAll("[^a-z0-9]+", "-") + ".pdf");
        empresaRepository.save(empresa);
    }

    private void salvarPessoaFisicaDemonstrativa(Usuario responsavel, Perfil perfil) {
        Empresa empresa = Empresa.builder()
                .tipoPessoa(TipoPessoa.FISICA)
                .nome("Pedro Almeida")
                .nomeFantasia("Pedro Almeida Consultoria")
                .cpf("52998224725")
                .faturamentoDireto(true)
                .status(StatusEmpresa.PENDENTE)
                .perfil(perfil)
                .responsavel(responsavel)
                .build();

        adicionarDocumentoObrigatorio(empresa, "documento-pedro-almeida-consultoria.pdf");
        empresaRepository.save(empresa);
    }

    private void salvarPessoaEstrangeiraDemonstrativa(Usuario responsavel, Perfil perfil) {
        Empresa empresa = Empresa.builder()
                .tipoPessoa(TipoPessoa.ESTRANGEIRA)
                .razaoSocial("Global Harbor Services LLC")
                .nomeFantasia("Global Harbor")
                .identificadorEstrangeiro("EXT-GHS-2026")
                .faturamentoDireto(false)
                .status(StatusEmpresa.PENDENTE)
                .perfil(perfil)
                .responsavel(responsavel)
                .build();

        adicionarDocumentoObrigatorio(empresa, "documento-global-harbor.pdf");
        empresaRepository.save(empresa);
    }

    private void adicionarDocumentoObrigatorio(Empresa empresa, String nomeArquivo) {
        empresa.getDocumentos().add(Documento.builder()
                .nomeArquivo(nomeArquivo)
                .tipoArquivo("PDF")
                .obrigatorio(true)
                .empresa(empresa)
                .build());
    }
}