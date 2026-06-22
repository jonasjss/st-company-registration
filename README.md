# 🚢 ST Company Registration

> Sistema de cadastro e gerenciamento de empresas desenvolvido como prova técnica para a **Super Terminais**, contemplando análise de requisitos, modelagem UML, desenvolvimento Full Stack e consultas SQL.

![Status](https://img.shields.io/badge/Status-Completed-success?style=for-the-badge)
![Technical Test](https://img.shields.io/badge/Technical_Test-Super_Terminais-0052CC?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-22-ED8B00?style=for-the-badge\&logo=openjdk\&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5-6DB33F?style=for-the-badge\&logo=springboot\&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-3-4FC08D?style=for-the-badge\&logo=vuedotjs\&logoColor=white)
![Vite](https://img.shields.io/badge/Vite-7-646CFF?style=for-the-badge\&logo=vite\&logoColor=white)
![Oracle SQL](https://img.shields.io/badge/Oracle_SQL-Database-F80000?style=for-the-badge\&logo=oracle\&logoColor=white)
![Git](https://img.shields.io/badge/Git-Version_Control-F05032?style=for-the-badge\&logo=git\&logoColor=white)
![UML](https://img.shields.io/badge/UML-Documentation-blue?style=for-the-badge)

---

## 📋 Sobre o Projeto

O sistema foi desenvolvido para atender aos requisitos da avaliação técnica da Super Terminais, permitindo o cadastro, gerenciamento e aprovação de empresas por usuários internos e externos.

A solução contempla os fluxos de:

* 🏢 Pessoa Jurídica
* 👤 Pessoa Física
* 🌎 Pessoa Estrangeira
* 👨‍💼 Usuário Interno
* 👨‍💻 Usuário Externo

Além da implementação dos fluxos de negócio, o projeto também inclui documentação UML e consultas SQL solicitadas na avaliação.

---

## 🎯 Objetivos

* Aplicar conceitos de Análise e Desenvolvimento de Sistemas.
* Desenvolver uma API REST utilizando Java e Spring Boot.
* Desenvolver uma interface moderna utilizando Vue.js.
* Modelar os requisitos utilizando UML.
* Implementar regras de negócio conforme especificação.
* Demonstrar organização, arquitetura e boas práticas de desenvolvimento.

---

## 🛠️ Tecnologias Utilizadas

### Backend

* Java 22
* Spring Boot
* Spring Data JPA
* Maven
* Bean Validation

### Frontend

* Vue 3
* Vite
* Axios
* CSS3

### Ferramentas

* Git
* GitHub
* Draw.io
* UML

---

## 📂 Estrutura do Projeto

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

## ✅ Funcionalidades Implementadas

### Cadastro de Empresas

* Cadastro de Pessoa Jurídica
* Cadastro de Pessoa Física
* Cadastro de Pessoa Estrangeira
* Associação de Perfil
* Upload de Documentos
* Validação de Campos Obrigatórios

### Gestão de Empresas

* Listagem de Empresas
* Consulta de Detalhes
* Aprovação de Empresas
* Reprovação de Empresas
* Atualização de Cadastro

### Validações

* CPF válido
* CNPJ válido
* Perfil obrigatório
* Documento obrigatório
* Tipos de arquivo permitidos
* Arquivos duplicados

---

## 📐 Diagramas UML

Os diagramas desenvolvidos para atender à avaliação encontram-se na pasta:

```text
docs/diagrams/
```

### Diagramas Entregues

* ✅ Diagrama de Caso de Uso
* ✅ Diagrama de Classe
* ✅ Diagrama de Atividade

---

## 🗄️ Banco de Dados

A modelagem foi construída com base nos requisitos funcionais da avaliação.

### Principais Entidades

* Empresa
* Documento
* Usuário
* Perfil

---

## 🧾 Consultas SQL

As consultas da Parte 02 da avaliação encontram-se na pasta:

```text
docs/sql/
```

### Consultas Implementadas

* Funcionários com cargos e departamentos
* Funcionários ativos
* Funcionários da Controladoria
* Funcionários com salário superior a R$ 2.900,00
* Quantidade de funcionários por departamento

---

## 🚀 Como Executar o Projeto

### Backend

```bash
cd backend/company-registration

./mvnw spring-boot:run
```

Servidor:

```text
http://localhost:8080
```

---

### Frontend

```bash
cd frontend

npm install

npm run dev
```

Aplicação:

```text
http://localhost:5173
```

---

## 📸 Demonstração

Adicionar imagens das telas em:

```text
docs/screenshots/
```

Sugestões:

* Tela de Cadastro
* Tela de Listagem
* Tela de Aprovação
* Tela de Reprovação
* Dashboard

---

## 📦 Entregáveis da Avaliação

### Parte 01

* ✅ Desenvolvimento Backend
* ✅ Desenvolvimento Frontend
* ✅ Regras de Negócio
* ✅ Fluxos Principais
* ✅ Fluxos Alternativos
* ✅ Validações
* ✅ Tratamento de Exceções

### Documentação

* ✅ Diagrama de Caso de Uso
* ✅ Diagrama de Classe
* ✅ Diagrama de Atividade

### Parte 02

* ✅ Consultas SQL
* ✅ Relacionamentos entre tabelas
* ✅ Consultas utilizando JOIN, WHERE e GROUP BY

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas:

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

## 👨‍💻 Desenvolvedor

**Jonas Santos**

GitHub:

```text
https://github.com/jonasjss
```

---

## 📄 Observação

Este projeto foi desenvolvido exclusivamente para fins de avaliação técnica e demonstração de conhecimentos em análise, modelagem e desenvolvimento de software.

---

