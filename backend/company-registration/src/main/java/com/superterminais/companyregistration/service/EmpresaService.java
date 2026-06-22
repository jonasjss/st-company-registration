package com.superterminais.companyregistration.service;

import com.superterminais.companyregistration.dto.DocumentoEmpresaRequestDTO;
import com.superterminais.companyregistration.dto.EmpresaRequestDTO;
import com.superterminais.companyregistration.dto.EmpresaResponseDTO;
import com.superterminais.companyregistration.entity.Documento;
import com.superterminais.companyregistration.entity.Empresa;
import com.superterminais.companyregistration.entity.Perfil;
import com.superterminais.companyregistration.entity.Usuario;
import com.superterminais.companyregistration.enums.PermissaoUsuario;
import com.superterminais.companyregistration.enums.StatusEmpresa;
import com.superterminais.companyregistration.enums.TipoPessoa;
import com.superterminais.companyregistration.enums.TipoUsuario;
import com.superterminais.companyregistration.exception.BusinessException;
import com.superterminais.companyregistration.exception.ResourceNotFoundException;
import com.superterminais.companyregistration.repository.EmpresaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class EmpresaService {

    private static final Set<String> FORMATOS_PERMITIDOS = Set.of("PDF", "PNG", "JPG", "JPEG");

    private final EmpresaRepository empresaRepository;
    private final PerfilService perfilService;
    private final UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<EmpresaResponseDTO> listar() {
        return empresaRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public EmpresaResponseDTO buscarPorId(Long id) {
        return toResponse(buscarEntidadePorId(id));
    }

    @Transactional
    public EmpresaResponseDTO criar(EmpresaRequestDTO dto) {
        Perfil perfil = perfilService.buscarEntidadePorId(dto.perfilId());
        Usuario responsavel = usuarioService.buscarEntidadePorId(dto.responsavelId());
        Usuario usuarioCadastro = dto.usuarioCadastroId() != null
                ? usuarioService.buscarEntidadePorId(dto.usuarioCadastroId())
                : responsavel;
        validarEmpresa(dto, true);
        validarPermissaoCadastro(usuarioCadastro);
        validarResponsavelExterno(usuarioCadastro, responsavel);

        Empresa empresa = Empresa.builder()
                .tipoPessoa(dto.tipoPessoa())
                .razaoSocial(dto.razaoSocial())
                .nome(dto.nome())
                .nomeFantasia(dto.nomeFantasia())
                .cnpj(dto.cnpj())
                .cpf(dto.cpf())
                .identificadorEstrangeiro(dto.identificadorEstrangeiro())
                .faturamentoDireto(Boolean.TRUE.equals(dto.faturamentoDireto()))
                .status(definirStatusInicial(usuarioCadastro))
                .perfil(perfil)
                .responsavel(responsavel)
                .build();

        adicionarDocumentos(empresa, dto.documentos());

        return toResponse(empresaRepository.save(empresa));
    }

    @Transactional
    public EmpresaResponseDTO atualizar(Long id, EmpresaRequestDTO dto) {
        Empresa empresa = buscarEntidadePorId(id);
        Perfil perfil = perfilService.buscarEntidadePorId(dto.perfilId());
        Usuario responsavel = usuarioService.buscarEntidadePorId(dto.responsavelId());
        validarEmpresa(dto, false);

        empresa.setTipoPessoa(dto.tipoPessoa());
        empresa.setRazaoSocial(dto.razaoSocial());
        empresa.setNome(dto.nome());
        empresa.setNomeFantasia(dto.nomeFantasia());
        empresa.setCnpj(dto.cnpj());
        empresa.setCpf(dto.cpf());
        empresa.setIdentificadorEstrangeiro(dto.identificadorEstrangeiro());
        empresa.setFaturamentoDireto(Boolean.TRUE.equals(dto.faturamentoDireto()));
        empresa.setStatus(dto.status() != null ? dto.status() : empresa.getStatus());
        empresa.setPerfil(perfil);
        empresa.setResponsavel(responsavel);

        return toResponse(empresa);
    }

    @Transactional
    public EmpresaResponseDTO aprovar(Long id, Long responsavelExternoId, Long usuarioInternoId) {
        Empresa empresa = buscarEntidadePorId(id);
        Usuario responsavelExterno = usuarioService.buscarEntidadePorId(responsavelExternoId);
        Usuario usuarioInterno = usuarioService.buscarEntidadePorId(usuarioInternoId);

        validarEmpresaPendente(empresa, "aprovar");
        validarUsuarioInternoComPermissaoEdicao(usuarioInterno);

        if (responsavelExterno.getTipoUsuario() != TipoUsuario.EXTERNO) {
            throw new BusinessException("Após a aprovação, o responsável vinculado deve ser um usuário externo.");
        }

        empresa.setStatus(StatusEmpresa.APROVADA);
        empresa.setResponsavel(responsavelExterno);
        return toResponse(empresa);
    }

    @Transactional
    public EmpresaResponseDTO reprovar(Long id, Long usuarioInternoId) {
        Empresa empresa = buscarEntidadePorId(id);
        Usuario usuarioInterno = usuarioService.buscarEntidadePorId(usuarioInternoId);
        validarEmpresaPendente(empresa, "reprovar");
        validarUsuarioInternoComPermissaoEdicao(usuarioInterno);
        empresa.setStatus(StatusEmpresa.REPROVADA);
        return toResponse(empresa);
    }

    @Transactional
    public void excluir(Long id) {
        Empresa empresa = buscarEntidadePorId(id);
        empresaRepository.delete(empresa);
    }

    public Empresa buscarEntidadePorId(Long id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empresa não encontrada: " + id));
    }

    private void validarEmpresa(EmpresaRequestDTO dto, boolean exigirDocumentoObrigatorio) {
        validarTextoObrigatorio(dto.nomeFantasia(), "Nome Fantasia", 3);

        if (dto.tipoPessoa() == TipoPessoa.JURIDICA) {
            validarTextoObrigatorio(dto.razaoSocial(), "Razão Social", 3);
            validarCnpj(dto.cnpj());
        }

        if (dto.tipoPessoa() == TipoPessoa.FISICA) {
            validarTextoObrigatorio(dto.nome(), "Nome", 3);
            validarCpf(dto.cpf());
        }

        if (dto.tipoPessoa() == TipoPessoa.ESTRANGEIRA) {
            validarTextoObrigatorio(dto.razaoSocial(), "Razão Social", 3);
            validarTextoObrigatorio(dto.identificadorEstrangeiro(), "Identificador Estrangeiro", 3);
        }

        validarDocumentos(dto.documentos(), exigirDocumentoObrigatorio);
    }

    private StatusEmpresa definirStatusInicial(Usuario usuarioCadastro) {
        return usuarioCadastro.getTipoUsuario() == TipoUsuario.INTERNO ? StatusEmpresa.APROVADA : StatusEmpresa.PENDENTE;
    }

    private void validarResponsavelExterno(Usuario usuarioCadastro, Usuario responsavel) {
        if (responsavel.getTipoUsuario() != TipoUsuario.EXTERNO) {
            throw new BusinessException("O responsável da empresa deve ser um usuário externo.");
        }

        if (usuarioCadastro.getTipoUsuario() == TipoUsuario.EXTERNO && !usuarioCadastro.getId().equals(responsavel.getId())) {
            throw new BusinessException("Quando o cadastro for realizado por usuário externo, o próprio usuário externo deve ser o responsável da empresa.");
        }
    }

    private void validarEmpresaPendente(Empresa empresa, String acao) {
        if (empresa.getStatus() != StatusEmpresa.PENDENTE) {
            throw new BusinessException("Somente empresas pendentes podem ser " + acao + ".");
        }
    }

    private void validarUsuarioInternoComPermissaoEdicao(Usuario usuario) {
        if (usuario.getTipoUsuario() != TipoUsuario.INTERNO) {
            throw new BusinessException("Somente usuário interno pode aprovar empresas.");
        }
        validarPermissao(usuario, PermissaoUsuario.EMPRESA_EDICAO);
    }

    private void validarPermissaoCadastro(Usuario usuario) {
        boolean podeCadastrar = usuario.getPermissoes() != null && (
                usuario.getPermissoes().contains(PermissaoUsuario.EMPRESA_CADASTRO)
                        || usuario.getPermissoes().contains(PermissaoUsuario.EMPRESA_MASTER)
                        || usuario.getPermissoes().contains(PermissaoUsuario.EMPRESA_EDICAO)
        );

        if (!podeCadastrar) {
            throw new BusinessException("Usuário sem permissão para cadastrar empresa.");
        }
    }

    private void validarPermissao(Usuario usuario, PermissaoUsuario permissao) {
        if (usuario.getPermissoes() == null || !usuario.getPermissoes().contains(permissao)) {
            throw new BusinessException("Usuário sem permissão: " + permissao.name());
        }
    }

    private void adicionarDocumentos(Empresa empresa, List<DocumentoEmpresaRequestDTO> documentos) {
        if (documentos == null) {
            return;
        }

        documentos.forEach(dto -> empresa.getDocumentos().add(Documento.builder()
                .nomeArquivo(dto.nomeArquivo())
                .tipoArquivo(normalizarTipoArquivo(dto.tipoArquivo()))
                .obrigatorio(dto.obrigatorio())
                .empresa(empresa)
                .build()));
    }

    private void validarDocumentos(List<DocumentoEmpresaRequestDTO> documentos, boolean exigirDocumentoObrigatorio) {
        if (exigirDocumentoObrigatorio && (documentos == null || documentos.stream().noneMatch(d -> Boolean.TRUE.equals(d.obrigatorio())))) {
            throw new BusinessException("É necessário enviar os arquivos obrigatórios.");
        }

        if (documentos == null || documentos.isEmpty()) {
            return;
        }

        Set<String> nomesArquivos = new HashSet<>();
        for (DocumentoEmpresaRequestDTO documento : documentos) {
            validarFormatoArquivo(documento.tipoArquivo());
            String nomeNormalizado = documento.nomeArquivo().trim().toLowerCase(Locale.ROOT);
            if (!nomesArquivos.add(nomeNormalizado)) {
                throw new BusinessException("O arquivo está duplicado: " + documento.nomeArquivo());
            }
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

    private void validarTextoObrigatorio(String valor, String campo, int minimo) {
        if (valor == null || valor.trim().length() < minimo) {
            throw new BusinessException(campo + " deve possuir no mínimo " + minimo + " caracteres.");
        }
    }

    private void validarCpf(String cpf) {
        if (cpf == null || !cpf.matches("\\d{11}") || possuiTodosDigitosIguais(cpf) || !digitosCpfValidos(cpf)) {
            throw new BusinessException("CPF inválido");
        }
    }

    private void validarCnpj(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}") || possuiTodosDigitosIguais(cnpj) || !digitosCnpjValidos(cnpj)) {
            throw new BusinessException("CNPJ fornecido inválido");
        }
    }

    private boolean possuiTodosDigitosIguais(String valor) {
        return valor.chars().distinct().count() == 1;
    }

    private boolean digitosCpfValidos(String cpf) {
        int primeiroDigito = calcularDigito(cpf.substring(0, 9), 10);
        int segundoDigito = calcularDigito(cpf.substring(0, 9) + primeiroDigito, 11);
        return cpf.equals(cpf.substring(0, 9) + primeiroDigito + segundoDigito);
    }

    private boolean digitosCnpjValidos(String cnpj) {
        int primeiroDigito = calcularDigitoCnpj(cnpj.substring(0, 12), new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        int segundoDigito = calcularDigitoCnpj(cnpj.substring(0, 12) + primeiroDigito, new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2});
        return cnpj.equals(cnpj.substring(0, 12) + primeiroDigito + segundoDigito);
    }

    private int calcularDigito(String base, int pesoInicial) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * (pesoInicial - i);
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    private int calcularDigitoCnpj(String base, int[] pesos) {
        int soma = 0;
        for (int i = 0; i < base.length(); i++) {
            soma += Character.getNumericValue(base.charAt(i)) * pesos[i];
        }
        int resto = soma % 11;
        return resto < 2 ? 0 : 11 - resto;
    }

    private EmpresaResponseDTO toResponse(Empresa empresa) {
        return new EmpresaResponseDTO(
                empresa.getId(),
                empresa.getTipoPessoa(),
                empresa.getRazaoSocial(),
                empresa.getNome(),
                empresa.getNomeFantasia(),
                empresa.getCnpj(),
                empresa.getCpf(),
                empresa.getIdentificadorEstrangeiro(),
                empresa.getFaturamentoDireto(),
                empresa.getStatus(),
                empresa.getDataCadastro(),
                empresa.getPerfil().getId(),
                empresa.getPerfil().getNome(),
                empresa.getResponsavel().getId(),
                empresa.getResponsavel().getNome()
        );
    }
}