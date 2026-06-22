# Frontend - Company Registration

Interface web desenvolvida em Vue + Vite para o **Portal de Serviço ST - Cadastro de Empresa**.

## Objetivo

Fornecer uma interface para demonstrar os fluxos principais do cadastro de empresa descritos no documento de requisitos:

- Cadastro de Pessoa Jurídica
- Cadastro de Pessoa Física
- Cadastro de Pessoa Estrangeira
- Listagem de empresas cadastradas
- Aprovação e reprovação de empresas pendentes
- Administração auxiliar de perfis e usuários

## Tecnologias

- Vue 3
- Vite
- JavaScript
- CSS puro
- Fetch API

## Como executar

Na pasta `frontend`, instale as dependências:

```bash
npm install
```

Execute o servidor de desenvolvimento:

```bash
npm run dev
```

A aplicação ficará disponível em:

```text
http://localhost:5173
```

## Integração com o backend

O Vite está configurado com proxy para redirecionar chamadas `/api` para o backend Spring Boot:

```text
Frontend: http://localhost:5173
Backend:  http://localhost:8080
```

Arquivo de configuração:

```text
vite.config.js
```

## Build de produção

```bash
npm run build
```

Os arquivos gerados ficam em:

```text
frontend/dist
```

## Funcionalidades da tela

### Cadastro de Empresa

O formulário exibe campos conforme o tipo de pessoa selecionado:

- `JURIDICA`: Razão Social e CNPJ
- `FISICA`: Nome e CPF
- `ESTRANGEIRA`: Razão Social e Identificador Estrangeiro

Campos comuns:

- Perfil
- Faturamento direto
- Usuário que cadastra
- Responsável externo
- Nome fantasia
- Documento obrigatório
- Documento opcional

### Empresas cadastradas

A tabela apresenta:

- Tipo de pessoa
- Nome/Razão social
- Nome fantasia
- Documento identificador conforme o tipo
- Perfil
- Responsável
- Status
- Ações disponíveis

Empresas aprovadas ou reprovadas não exibem ações. Apenas empresas pendentes podem ser aprovadas/reprovadas.

### Modais

Foram implementados modais para os principais fluxos do documento:

- Sucesso no cadastro de empresa
- Sucesso na aprovação
- Sucesso na reprovação
- Avisos e erros de validação
- Confirmação de reprovação

Os modais foram inspirados nas mensagens M01 a M13 do documento.

## Logo

A logo do projeto está em:

```text
src/assets/Logo.png
```

Ela é importada diretamente no componente principal `App.vue`.

## Observações

- O upload real de arquivos não foi implementado; a interface registra nome e tipo do documento para demonstrar a regra de anexos.
- A autenticação por certificado digital é uma pré-condição do documento, mas não foi implementada neste protótipo.
- A seção **Dados auxiliares / Administração** existe para facilitar a criação de perfis e usuários durante a avaliação.