<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import { api } from './services/api'
import logoSuperTerminais from './assets/Logo.png'

const perfis = ref([])
const usuarios = ref([])
const empresas = ref([])
const loading = ref(false)
const modal = reactive({
  aberto: false,
  titulo: '',
  mensagem: '',
  tipo: 'info',
  confirmarTexto: '',
  cancelarTexto: '',
  aoConfirmar: null
})

const MODAIS_EMPRESA = {
  M01: { titulo: 'Sucesso', mensagem: 'Empresa cadastrada com sucesso.', tipo: 'success', confirmarTexto: 'Fechar' },
  M02: { titulo: 'Aviso', mensagem: 'É necessário enviar os arquivos obrigatórios para prosseguir', tipo: 'warning', confirmarTexto: 'Fechar' },
  M03: { titulo: 'Atenção', mensagem: 'Ocorreu um erro ao encontrar o perfil', tipo: 'warning', confirmarTexto: 'Fechar' },
  M04: { titulo: 'Atenção', mensagem: 'CNPJ fornecido inválido', tipo: 'warning', confirmarTexto: 'Fechar' },
  M05: { titulo: 'Atenção', mensagem: 'CPF inválido', tipo: 'warning', confirmarTexto: 'Fechar' },
  M06: { titulo: 'Arquivo duplicado', mensagem: 'Arquivo duplicado', tipo: 'warning', confirmarTexto: 'Fechar' },
  M07: { titulo: 'Arquivo inválido', mensagem: 'Tamanho de arquivo não suportado.', tipo: 'warning', confirmarTexto: 'Fechar' },
  M08: { titulo: 'Arquivo inválido', mensagem: 'São válidos somente arquivos do tipo: pdf, png, jpg ou jpeg.', tipo: 'warning', confirmarTexto: 'Fechar' },
  M09: { titulo: 'Remover arquivo', mensagem: 'Deseja realmente remover este arquivo?', tipo: 'danger', cancelarTexto: 'Cancelar', confirmarTexto: 'Confirmar' },
  M10: { titulo: 'Sucesso', mensagem: 'Empresa aprovada com sucesso', tipo: 'success', confirmarTexto: 'Fechar' },
  M11: { mensagem: '[S0]', tipo: 'danger', cancelarTexto: 'Voltar', confirmarTexto: 'Reprovar' },
  M12: { titulo: 'Sucesso', mensagem: 'Empresa reprovada com sucesso', tipo: 'success', confirmarTexto: 'Fechar' },
  M13: { titulo: 'Sucesso', mensagem: 'Empresa atualizada com sucesso', tipo: 'success', confirmarTexto: 'Fechar' }
}

const perfilForm = reactive({ nome: '' })
const usuarioForm = reactive({ nome: '', email: '', tipoUsuario: 'EXTERNO' })
const empresaForm = reactive({
  tipoPessoa: 'JURIDICA',
  razaoSocial: '',
  nome: '',
  nomeFantasia: '',
  cnpj: '',
  cpf: '',
  identificadorEstrangeiro: '',
  faturamentoDireto: false,
  perfilId: '',
  usuarioCadastroId: '',
  responsavelId: '',
  documentoObrigatorio: 'contrato-social.pdf',
  tipoArquivo: 'PDF',
  documentoOpcional: '',
  tipoArquivoOpcional: 'PDF'
})

const usuariosExternos = computed(() => usuarios.value.filter((u) => u.tipoUsuario === 'EXTERNO'))
const usuariosInternosComEdicao = computed(() => usuarios.value.filter((u) =>
  u.tipoUsuario === 'INTERNO' && Array.isArray(u.permissoes) && u.permissoes.includes('EMPRESA_EDICAO')
))
const usuarioCadastroSelecionado = computed(() => usuarios.value.find((u) => u.id === Number(empresaForm.usuarioCadastroId)))
const isPessoaJuridica = computed(() => empresaForm.tipoPessoa === 'JURIDICA')
const isPessoaFisica = computed(() => empresaForm.tipoPessoa === 'FISICA')
const isPessoaEstrangeira = computed(() => empresaForm.tipoPessoa === 'ESTRANGEIRA')

function limparFeedback() {
  fecharModal()
}

function abrirModal({ titulo, mensagem, tipo = 'info', confirmarTexto = 'Fechar', cancelarTexto = '', aoConfirmar = null }) {
  modal.titulo = titulo
  modal.mensagem = mensagem
  modal.tipo = tipo
  modal.confirmarTexto = confirmarTexto
  modal.cancelarTexto = cancelarTexto
  modal.aoConfirmar = aoConfirmar
  modal.aberto = true
}

function abrirModalDocumento(idModal, extras = {}) {
  abrirModal({ ...MODAIS_EMPRESA[idModal], ...extras })
}

function fecharModal() {
  modal.aberto = false
  modal.aoConfirmar = null
}

async function confirmarModal() {
  const acao = modal.aoConfirmar
  fecharModal()
  if (acao) await acao()
}

function abrirModalErro(error) {
  const mensagem = error.message || 'Erro inesperado'
  if (mensagem.includes('CNPJ')) {
    abrirModalDocumento('M04')
    return
  }
  if (mensagem.includes('CPF')) {
    abrirModalDocumento('M05')
    return
  }
  if (mensagem.toLowerCase().includes('perfil')) {
    abrirModalDocumento('M03')
    return
  }
  if (mensagem.toLowerCase().includes('arquivo') && mensagem.toLowerCase().includes('duplic')) {
    abrirModalDocumento('M06')
    return
  }
  if (mensagem.toLowerCase().includes('tamanho')) {
    abrirModalDocumento('M07')
    return
  }
  if (mensagem.toLowerCase().includes('formato') || mensagem.toLowerCase().includes('tipo')) {
    abrirModalDocumento('M08')
    return
  }
  if (mensagem.toLowerCase().includes('obrigat')) {
    abrirModalDocumento('M02')
    return
  }
  abrirModal({ titulo: 'Atenção', mensagem, tipo: 'warning' })
}

function removerDocumento(campoDocumento) {
  empresaForm[campoDocumento] = ''
}

function confirmarRemocaoDocumento(campoDocumento) {
  abrirModalDocumento('M09', {
    aoConfirmar: () => removerDocumento(campoDocumento)
  })
}

function limparCamposPorTipoPessoa() {
  empresaForm.razaoSocial = ''
  empresaForm.nome = ''
  empresaForm.cnpj = ''
  empresaForm.cpf = ''
  empresaForm.identificadorEstrangeiro = ''
}

function atualizarResponsavelPorUsuarioCadastro() {
  if (usuarioCadastroSelecionado.value?.tipoUsuario === 'EXTERNO') {
    empresaForm.responsavelId = empresaForm.usuarioCadastroId
    return
  }

  if (!usuariosExternos.value.some((usuario) => usuario.id === Number(empresaForm.responsavelId))) {
    empresaForm.responsavelId = ''
  }
}

function documentoEmpresa(empresa) {
  if (empresa.tipoPessoa === 'JURIDICA') return empresa.cnpj || '-'
  if (empresa.tipoPessoa === 'FISICA') return empresa.cpf || '-'
  return empresa.identificadorEstrangeiro || '-'
}

function rotuloDocumentoEmpresa(empresa) {
  if (empresa.tipoPessoa === 'JURIDICA') return 'CNPJ'
  if (empresa.tipoPessoa === 'FISICA') return 'CPF'
  return 'Identificador estrangeiro'
}

async function carregarDados() {
  loading.value = true
  limparFeedback()
  try {
    const [p, u, e] = await Promise.all([
      api.listarPerfis(),
      api.listarUsuarios(),
      api.listarEmpresas()
    ])
    perfis.value = p
    usuarios.value = u
    empresas.value = e
  } catch (error) {
    abrirModalErro(error)
  } finally {
    loading.value = false
  }
}

async function salvarPerfil() {
  limparFeedback()
  try {
    await api.criarPerfil({ nome: perfilForm.nome })
    perfilForm.nome = ''
    abrirModal({ titulo: 'Sucesso', mensagem: 'Perfil cadastrado com sucesso.', tipo: 'success' })
    await carregarDados()
  } catch (error) {
    abrirModalErro(error)
  }
}

async function salvarUsuario() {
  limparFeedback()
  try {
    await api.criarUsuario({ ...usuarioForm })
    usuarioForm.nome = ''
    usuarioForm.email = ''
    abrirModal({ titulo: 'Sucesso', mensagem: 'Usuário cadastrado com sucesso.', tipo: 'success' })
    await carregarDados()
  } catch (error) {
    abrirModalErro(error)
  }
}

function montarEmpresaPayload() {
  const documentos = [
    {
      nomeArquivo: empresaForm.documentoObrigatorio,
      tipoArquivo: empresaForm.tipoArquivo,
      obrigatorio: true
    }
  ]

  if (empresaForm.documentoOpcional) {
    documentos.push({
      nomeArquivo: empresaForm.documentoOpcional,
      tipoArquivo: empresaForm.tipoArquivoOpcional,
      obrigatorio: false
    })
  }

  const payload = {
    tipoPessoa: empresaForm.tipoPessoa,
    razaoSocial: isPessoaJuridica.value || isPessoaEstrangeira.value ? empresaForm.razaoSocial || null : null,
    nome: isPessoaFisica.value ? empresaForm.nome || null : null,
    nomeFantasia: empresaForm.nomeFantasia || null,
    cnpj: isPessoaJuridica.value ? empresaForm.cnpj || null : null,
    cpf: isPessoaFisica.value ? empresaForm.cpf || null : null,
    identificadorEstrangeiro: isPessoaEstrangeira.value ? empresaForm.identificadorEstrangeiro || null : null,
    faturamentoDireto: empresaForm.faturamentoDireto,
    perfilId: Number(empresaForm.perfilId),
    usuarioCadastroId: Number(empresaForm.usuarioCadastroId),
    responsavelId: Number(empresaForm.responsavelId),
    documentos
  }
  return payload
}

async function salvarEmpresa() {
  limparFeedback()
  try {
    await api.criarEmpresa(montarEmpresaPayload())
    abrirModalDocumento('M01')
    empresaForm.razaoSocial = ''
    empresaForm.nome = ''
    empresaForm.nomeFantasia = ''
    empresaForm.cnpj = ''
    empresaForm.cpf = ''
    empresaForm.identificadorEstrangeiro = ''
    empresaForm.documentoOpcional = ''
    await carregarDados()
  } catch (error) {
    abrirModalErro(error)
  }
}

async function aprovar(empresa) {
  limparFeedback()
  const responsavel = usuariosExternos.value[0]
  const usuarioInterno = usuariosInternosComEdicao.value[0]
  if (!responsavel) {
    abrirModal({ titulo: 'Aviso', mensagem: 'Cadastre um usuário externo para aprovar empresas.', tipo: 'warning' })
    return
  }
  if (!usuarioInterno) {
    abrirModal({ titulo: 'Aviso', mensagem: 'Cadastre um usuário interno com permissão de edição para aprovar empresas.', tipo: 'warning' })
    return
  }
  try {
    await api.aprovarEmpresa(empresa.id, responsavel.id, usuarioInterno.id)
    abrirModalDocumento('M10')
    await carregarDados()
  } catch (error) {
    abrirModalErro(error)
  }
}

async function reprovar(empresa) {
  limparFeedback()
  abrirModal({
    ...MODAIS_EMPRESA.M11,
    titulo: `Reprovar Empresa - ${empresa.nomeFantasia || empresa.razaoSocial || empresa.nome}`,
    aoConfirmar: () => confirmarReprovacao(empresa)
  })
}

async function confirmarReprovacao(empresa) {
  const usuarioInterno = usuariosInternosComEdicao.value[0]
  if (!usuarioInterno) {
    abrirModal({ titulo: 'Aviso', mensagem: 'Cadastre um usuário interno com permissão de edição para reprovar empresas.', tipo: 'warning' })
    return
  }
  try {
    await api.reprovarEmpresa(empresa.id, usuarioInterno.id)
    abrirModalDocumento('M12')
    await carregarDados()
  } catch (error) {
    abrirModalErro(error)
  }
}

onMounted(carregarDados)
</script>

<template>
  <main>
    <header class="topbar">
      <div class="brand">
        <img class="brand-logo" :src="logoSuperTerminais" alt="Super Terminais" />
        <div>
          <strong>Super Terminais</strong>
          <span>Portal de Serviços</span>
        </div>
      </div>
      <nav>
        <a href="#cadastro">Cadastro</a>
        <a href="#empresas">Empresas</a>
        <a href="#administracao">Administração</a>
        <a href="http://localhost:8080/swagger-ui.html" target="_blank">Swagger</a>
      </nav>
    </header>

    <section class="hero">
      <div class="hero-content">
        <p class="eyebrow">Terminal portuário • Cadastro corporativo</p>
        <h1>Cadastro de Empresa</h1>
        <p>
          Gerencie empresas, perfis, usuários responsáveis e aprovação cadastral em um fluxo integrado ao backend Spring Boot.
        </p>
        <div class="hero-actions">
          <a class="primary-link" href="#cadastro">Iniciar cadastro</a>
          <a class="secondary-link" href="#empresas">Ver empresas cadastradas</a>
        </div>
      </div>
      <div class="hero-panel">
        <span>Empresas</span>
        <strong>{{ empresas.length }}</strong>
        <small>registros carregados</small>
      </div>
    </section>

    <div class="page">
      <div v-if="loading" class="alert info">Carregando...</div>

    <form id="cadastro" class="card featured" @submit.prevent="salvarEmpresa">
      <div class="card-heading">
        <div>
          <p class="eyebrow dark">Fluxo principal</p>
          <h2>Cadastro de Empresa</h2>
          <p class="section-description">
            Cadastre pessoa jurídica, física ou estrangeira conforme o documento de requisitos.
          </p>
        </div>
      </div>

      <div class="grid three">
        <div>
          <label>Tipo pessoa</label>
          <select v-model="empresaForm.tipoPessoa" @change="limparCamposPorTipoPessoa">
            <option>JURIDICA</option>
            <option>FISICA</option>
            <option>ESTRANGEIRA</option>
          </select>
        </div>

        <div>
          <label>Perfil</label>
          <select v-model="empresaForm.perfilId" required>
            <option disabled value="">Selecione</option>
            <option v-for="perfil in perfis" :key="perfil.id" :value="perfil.id">{{ perfil.nome }}</option>
          </select>
        </div>

        <div>
          <label>Faturamento direto</label>
          <select v-model="empresaForm.faturamentoDireto">
            <option :value="false">Não</option>
            <option :value="true">Sim</option>
          </select>
        </div>
      </div>

      <div class="grid three">
        <div>
          <label>Usuário que cadastra</label>
          <select v-model="empresaForm.usuarioCadastroId" required @change="atualizarResponsavelPorUsuarioCadastro">
            <option disabled value="">Selecione</option>
            <option v-for="usuario in usuarios" :key="usuario.id" :value="usuario.id">
              {{ usuario.nome }} - {{ usuario.tipoUsuario }}
            </option>
          </select>
        </div>
        <div>
          <label>Responsável externo</label>
          <select v-model="empresaForm.responsavelId" required :disabled="usuarioCadastroSelecionado?.tipoUsuario === 'EXTERNO'">
            <option disabled value="">Selecione</option>
            <option v-for="usuario in usuariosExternos" :key="usuario.id" :value="usuario.id">
              {{ usuario.nome }} - EXTERNO
            </option>
          </select>
          <small class="field-help">
            O responsável deve ser um usuário externo. Para cadastro externo, o próprio usuário é usado automaticamente.
          </small>
        </div>
        <div>
          <label>Nome fantasia</label>
          <input v-model="empresaForm.nomeFantasia" required minlength="3" placeholder="Empresa ST" />
        </div>
      </div>

      <div class="grid three">
        <div v-if="isPessoaJuridica || isPessoaEstrangeira">
          <label>Razão social</label>
          <input v-model="empresaForm.razaoSocial" required minlength="3" placeholder="Empresa Teste LTDA" />
        </div>
        <div v-if="isPessoaFisica">
          <label>Nome</label>
          <input v-model="empresaForm.nome" required minlength="3" placeholder="João da Silva" />
        </div>
        <div v-if="isPessoaEstrangeira">
          <label>Identificador estrangeiro</label>
          <input v-model="empresaForm.identificadorEstrangeiro" required minlength="3" placeholder="EXT-12345" />
        </div>
        <div v-if="isPessoaJuridica">
          <label>CNPJ</label>
          <input v-model="empresaForm.cnpj" required minlength="14" maxlength="14" placeholder="11222333000181" />
        </div>
        <div v-if="isPessoaFisica">
          <label>CPF</label>
          <input v-model="empresaForm.cpf" required minlength="11" maxlength="11" placeholder="52998224725" />
        </div>
      </div>

      <div class="grid three">
        <div>
          <label>Documento obrigatório</label>
          <input v-model="empresaForm.documentoObrigatorio" required placeholder="contrato-social.pdf" />
          <button
            v-if="empresaForm.documentoObrigatorio"
            type="button"
            class="small outline field-action"
            @click="confirmarRemocaoDocumento('documentoObrigatorio')"
          >
            Remover arquivo
          </button>
        </div>
        <div>
          <label>Tipo arquivo</label>
          <select v-model="empresaForm.tipoArquivo">
            <option>PDF</option>
            <option>PNG</option>
            <option>JPG</option>
            <option>JPEG</option>
          </select>
        </div>
        <div>
          <label>Documento opcional</label>
          <input v-model="empresaForm.documentoOpcional" placeholder="procuracao.pdf" />
          <button
            v-if="empresaForm.documentoOpcional"
            type="button"
            class="small outline field-action"
            @click="confirmarRemocaoDocumento('documentoOpcional')"
          >
            Remover arquivo
          </button>
        </div>
        <div>
          <label>Tipo arquivo opcional</label>
          <select v-model="empresaForm.tipoArquivoOpcional">
            <option>PDF</option>
            <option>PNG</option>
            <option>JPG</option>
            <option>JPEG</option>
          </select>
        </div>
      </div>

      <button>Cadastrar empresa</button>
    </form>

    <section id="empresas" class="card">
      <div class="card-heading horizontal">
        <div>
          <p class="eyebrow dark">Acompanhamento</p>
          <h2>Empresas cadastradas</h2>
        </div>
        <span class="count-pill">{{ empresas.length }} registros</span>
      </div>
      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th>Empresa</th>
              <th>Perfil</th>
              <th>Responsável</th>
              <th>Status</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="empresa in empresas" :key="empresa.id">
              <td class="company-cell">
                <strong>{{ empresa.razaoSocial || empresa.nome }}</strong>
                <small class="table-detail">
                  {{ empresa.tipoPessoa }} • {{ empresa.nomeFantasia }}<br />
                  {{ rotuloDocumentoEmpresa(empresa) }}: {{ documentoEmpresa(empresa) }} •
                  Faturamento direto: {{ empresa.faturamentoDireto ? 'Sim' : 'Não' }}
                </small>
              </td>
              <td>{{ empresa.perfilNome }}</td>
              <td>{{ empresa.responsavelNome }}</td>
              <td><span class="badge" :class="empresa.status.toLowerCase()">{{ empresa.status }}</span></td>
              <td v-if="empresa.status === 'PENDENTE'" class="actions-cell">
                <button type="button" class="small" @click="aprovar(empresa)">Aprovar</button>
                <button type="button" class="small danger" @click="reprovar(empresa)">Reprovar</button>
              </td>
              <td v-else class="actions-cell">
                <span class="muted-action">Sem ações</span>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>

    <section id="administracao" class="admin-section">
      <div class="section-heading">
        <p class="eyebrow dark">Dados auxiliares / Administração</p>
        <h2>Perfis e usuários</h2>
        <p>
          Cadastre aqui os dados necessários para alimentar os campos de perfil, usuário que cadastra e responsável da empresa.
        </p>
      </div>

      <div class="grid two">
        <form class="card" @submit.prevent="salvarPerfil">
          <h2>Perfil</h2>
          <label>Nome do perfil</label>
          <input v-model="perfilForm.nome" required minlength="3" placeholder="Despachante" />
          <button>Cadastrar perfil</button>
        </form>

        <form class="card" @submit.prevent="salvarUsuario">
          <h2>Usuário</h2>
          <label>Nome</label>
          <input v-model="usuarioForm.nome" required minlength="3" placeholder="Maria Souza" />
          <label>E-mail</label>
          <input v-model="usuarioForm.email" required type="email" placeholder="maria@email.com" />
          <label>Tipo</label>
          <select v-model="usuarioForm.tipoUsuario">
            <option>EXTERNO</option>
            <option>INTERNO</option>
          </select>
          <button>Cadastrar usuário</button>
        </form>
      </div>
    </section>
    </div>

    <div v-if="modal.aberto" class="modal-backdrop" @click.self="fecharModal">
      <section class="modal-card" :class="modal.tipo">
        <div class="modal-icon">
          <span v-if="modal.tipo === 'success'">✓</span>
          <span v-else-if="modal.tipo === 'danger'">!</span>
          <span v-else>!</span>
        </div>
        <div class="modal-content">
          <h2>{{ modal.titulo }}</h2>
          <p>{{ modal.mensagem }}</p>
          <div class="modal-actions">
            <button v-if="modal.cancelarTexto" type="button" class="outline" @click="fecharModal">
              {{ modal.cancelarTexto }}
            </button>
            <button type="button" :class="{ danger: modal.tipo === 'danger' }" @click="confirmarModal">
              {{ modal.confirmarTexto || 'Fechar' }}
            </button>
          </div>
        </div>
      </section>
    </div>
  </main>
</template>