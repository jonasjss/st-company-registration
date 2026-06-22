# 🚢 ST Company Registration

> Sistema de cadastro e gerenciamento de empresas desenvolvido como prova técnica para a **Super Terminais**, contemplando análise de requisitos, modelagem UML, desenvolvimento Full Stack, documentação de API e consultas SQL.

![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)
![Technical Test](https://img.shields.io/badge/Technical_Test-Super_Terminais-0052CC?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-22-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-3-4FC08D?style=for-the-badge\&logo=vuedotjs\&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-7-646CFF?style=for-the-badge\&logo=vite\&logoColor=white)
![Oracle SQL](https://img.shields.io/badge/Oracle_SQL-Database-F80000?style=for-the-badge\&logo=oracle\&logoColor=white)
![Swagger](https://img.shields.io/badge/Swagger-OpenAPI-85EA2D?style=for-the-badge\&logo=swagger\&logoColor=black)
![Git](https://img.shields.io/badge/Git-Version_Control-F05032?style=for-the-badge\&logo=git\&logoColor=white)
![UML](https://img.shields.io/badge/UML-Documentation-blue?style=for-the-badge)

---

# 📋 Sobre o Projeto

O **ST Company Registration** foi desenvolvido para atender aos requisitos da avaliação técnica da Super Terminais.

A solução permite o cadastro, gerenciamento e aprovação de empresas por usuários internos e externos, seguindo as regras de negócio definidas na especificação funcional.

O sistema contempla:

* 🏢 Cadastro de Pessoa Jurídica
* 👤 Cadastro de Pessoa Física
* 🌎 Cadastro de Pessoa Estrangeira
* 📋 Gerenciamento de Empresas
* ✅ Aprovação de Cadastros
* ❌ Reprovação de Cadastros
* 👥 Administração de Usuários
* 🏷️ Administração de Perfis
* 📄 Gerenciamento de Documentos
* 🔌 API REST Documentada com Swagger

---

# ✨ Diferenciais Implementados

* Interface moderna desenvolvida com Vue 3
* API REST utilizando Spring Boot
* Documentação automática com Swagger/OpenAPI
* Fluxos distintos para Pessoa Jurídica, Física e Estrangeira
* Aprovação e reprovação de empresas
* Administração de usuários e perfis
* Validação de CPF
* Validação de CNPJ
* Upload de documentos obrigatórios e opcionais
* Arquitetura em camadas
* Diagramas UML completos
* Consultas SQL solicitadas na avaliação

---

# 🛠️ Tecnologias Utilizadas

## Backend

* Java 22
* Spring Boot
* Spring Data JPA
* Maven
* Bean Validation
* OpenAPI / Swagger

## Frontend

* Vue 3
* Vite
* Axios
* CSS3

## Ferramentas

* Git
* GitHub
* Draw.io
* UML

---

# 🏗️ Arquitetura

```text
Frontend (Vue 3)
        │
        ▼
REST API (Spring Boot)
        │
        ▼
Camada de Serviço
        │
        ▼
Camada de Persistência
        │
        ▼
Banco de Dados
```

---

# 📂 Estrutura do Projeto

```text
st-company-registration/
│
├── backend/
│   └── company-registration/
│
├── frontend/
│
├── docs/
│   ├── diagrams/
│   ├── sql/
│   └── screenshots/
│
└── README.md
```

---

# ✅ Funcionalidades Implementadas

## Cadastro de Empresas

* Cadastro de Pessoa Jurídica
* Cadastro de Pessoa Física
* Cadastro de Pessoa Estrangeira
* Associação de Perfil
* Associação de Responsável
* Upload de Documentos
* Faturamento Direto

## Gestão de Empresas

* Listagem de Empresas
* Aprovação de Empresas
* Reprovação de Empresas
* Consulta de Empresas
* Atualização de Status

## Administração

* Cadastro de Perfis
* Cadastro de Usuários
* Controle de Responsáveis

## Validações

* CPF válido
* CNPJ válido
* Perfil obrigatório
* Documento obrigatório
* Arquivos duplicados
* Tipos de arquivo permitidos

---

# 📸 Demonstração do Sistema

## 🏠 Tela Principal do Sistema

Visão geral da aplicação com acesso rápido às funcionalidades principais.

![Tela Principal](docs/screenshots/Tela%20Principal%20do%20Sistema.png)

---

## 🏢 Cadastro de Empresa – Pessoa Jurídica

Fluxo de cadastro para empresas nacionais utilizando CNPJ.

![Pessoa Jurídica](docs/screenshots/Cadastro%20de%20Empresa%20-%20Juridica.png)

---

## 👤 Cadastro de Empresa – Pessoa Física

Fluxo de cadastro para pessoas físicas utilizando CPF.

![Pessoa Física](docs/screenshots/Cadastro%20de%20Empresa%20-%20Fisica.png)

---

## 🌎 Cadastro de Empresa – Pessoa Estrangeira

Fluxo de cadastro para empresas estrangeiras utilizando identificador internacional.

![Pessoa Estrangeira](docs/screenshots/Cadastro%20de%20Empresa%20-%20Estrangeira.png)

---

## 📋 Listagem e Aprovação de Empresas

Tela de acompanhamento dos cadastros com aprovação e reprovação de solicitações.

![Empresas](docs/screenshots/Empresa%20Cadastradas.png)

---

## ⚙️ Administração de Perfis e Usuários

Gerenciamento dos perfis e usuários utilizados nos fluxos do sistema.

![Administração](docs/screenshots/Administracao.png)

---

## 🔌 Documentação da API

Documentação automática dos endpoints através do Swagger/OpenAPI.

![Swagger](docs/screenshots/APIs.png)

---

# 📐 Diagramas UML

Os diagramas desenvolvidos para atender aos requisitos da avaliação encontram-se na pasta:

```text
docs/diagrams/
```

## Diagramas Entregues

* ✅ Diagrama de Caso de Uso
* ✅ Diagrama de Classe
* ✅ Diagrama de Atividade

---

# 🧾 Consultas SQL

As consultas da Parte 02 encontram-se na pasta:

```text
docs/sql/
```

Consultas implementadas:

* Funcionários com cargos e departamentos
* Funcionários ativos
* Funcionários da Controladoria
* Funcionários com salário superior a R$ 2.900,00
* Quantidade de funcionários por departamento

---

# 📦 Requisitos Atendidos

| Requisito                 | Status |
| ------------------------- | ------ |
| Backend Java              | ✅      |
| Frontend Vue.js           | ✅      |
| Cadastro de Empresas      | ✅      |
| Aprovação/Reprovação      | ✅      |
| Administração de Usuários | ✅      |
| Administração de Perfis   | ✅      |
| Swagger/OpenAPI           | ✅      |
| Diagrama de Caso de Uso   | ✅      |
| Diagrama de Classe        | ✅      |
| Diagrama de Atividade     | ✅      |
| Consultas SQL             | ✅      |
| Documentação Técnica      | ✅      |

---

# 🚀 Como Executar o Projeto

## Backend

### Windows

```bash
cd backend/company-registration

mvnw.cmd spring-boot:run
```

### Linux / macOS

```bash
cd backend/company-registration

./mvnw spring-boot:run
```

Aplicação disponível em:

```text
http://localhost:8080
```

Swagger:

```text
http://localhost:8080/swagger-ui/index.html
```

---

## Frontend

```bash
cd frontend

npm install

npm run dev
```

Aplicação disponível em:

```text
http://localhost:5173
```

---

# 👨‍💻 Desenvolvedor

**Jonas Santos**

Desenvolvedor Full Stack

GitHub:

https://github.com/jonasjss

---

# 📄 Observação

Projeto desenvolvido exclusivamente para fins de avaliação técnica da Super Terminais, com o objetivo de demonstrar conhecimentos em análise de sistemas, modelagem UML, desenvolvimento Full Stack e banco de dados.

---
