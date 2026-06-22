# Backend - Company Registration

API REST desenvolvida em Spring Boot para atender a Parte 01 do teste técnico: **Portal de Serviço ST - Cadastro de Empresa**.

## Objetivo

Fornecer os recursos de backend para cadastro, listagem, aprovação e reprovação de empresas dos tipos:

- Pessoa Jurídica
- Pessoa Física
- Pessoa Estrangeira

O backend centraliza as regras de negócio, validações e permissões descritas no documento de requisitos.

## Tecnologias

- Java 22
- Spring Boot 3.5
- Spring Web
- Spring Data JPA
- Bean Validation
- H2 Database em memória
- Lombok
- Springdoc OpenAPI / Swagger

## Como executar

Na pasta `backend/company-registration`, execute:

```bash
./mvnw spring-boot:run
```

No Windows/PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

A API ficará disponível em:

```text
http://localhost:8080
```

## Swagger

```text
http://localhost:8080/swagger-ui.html
```

## Banco H2

Console H2:

```text
http://localhost:8080/h2-console
```

Configuração:

```text
JDBC URL: jdbc:h2:mem:company_registration
User: sa
Password: vazio
```

## Dados demonstrativos

O projeto possui carga inicial em `config/DataInitializer.java`.

Ao iniciar com banco vazio, são cadastrados automaticamente:

- Perfis: Despachante, Beneficiário e Consignatário
- Usuários fictícios externo e interno
- Empresas fictícias com status diferentes
- Exemplos dos três tipos de pessoa: jurídica, física e estrangeira

Esses dados existem apenas para facilitar a avaliação técnica e demonstrar os fluxos.

## Principais endpoints

### Empresas

```text
GET    /api/empresas
GET    /api/empresas/{id}
POST   /api/empresas
PUT    /api/empresas/{id}
PATCH  /api/empresas/{id}/aprovar
PATCH  /api/empresas/{id}/reprovar
DELETE /api/empresas/{id}
```

### Perfis

```text
GET    /api/perfis
POST   /api/perfis
PUT    /api/perfis/{id}
DELETE /api/perfis/{id}
```

### Usuários

```text
GET    /api/usuarios
POST   /api/usuarios
PUT    /api/usuarios/{id}
DELETE /api/usuarios/{id}
```

## Regras implementadas

- Cadastro de empresa jurídica, física e estrangeira.
- Validação de CPF e CNPJ.
- Perfil obrigatório.
- Documento obrigatório.
- Validação de formatos: PDF, PNG, JPG e JPEG.
- Bloqueio de documento duplicado.
- Usuário externo cadastra empresa como `PENDENTE`.
- Usuário interno cadastra empresa como `APROVADA`.
- Responsável da empresa deve ser usuário externo.
- Apenas empresas `PENDENTE` podem ser aprovadas ou reprovadas.
- Aprovação/reprovação exigem usuário interno com permissão de edição.

## Permissões

Permissões baseadas na seção de atores do documento:

- Usuário externo:
  - `EMPRESA_CADASTRO`
- Usuário interno:
  - `EMPRESA_MASTER`
  - `EMPRESA_LISTA`
  - `EMPRESA_EDICAO`

## Testes/build

```bash
./mvnw test
```

No Windows/PowerShell:

```powershell
.\mvnw.cmd test
```

## Observações

- A autenticação por certificado digital é tratada como pré-condição do fluxo, conforme o documento, mas não foi implementada neste protótipo.
- O armazenamento real de arquivos não foi implementado; o sistema registra metadados demonstrativos do documento.