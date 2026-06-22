# Sistema de Cadastro de Empresas - Portal de Serviços ST

## Sobre o Projeto

Este projeto foi desenvolvido como solução para a prova técnica da Super Terminais.

O sistema tem como objetivo permitir o cadastro e gerenciamento de empresas por usuários internos e externos, contemplando os fluxos de Pessoa Jurídica, Pessoa Física e Pessoa Estrangeira, além do processo de aprovação, reprovação e vinculação de responsável.

---

## Tecnologias Utilizadas

### Backend

* Java 22
* Spring Boot 3.5
* Spring Data JPA
* Maven
* H2 Database
* Oracle SQL (compatível com Oracle 12c)

### Frontend

* Vue 3
* Vite
* JavaScript

### Ferramentas

* Git
* GitHub
* Draw.io
* VS Code

---

## Estrutura do Projeto

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
│   │   ├── drawio/
│   │   └── images/
│   │
│   ├── requirements/
│   └── sql/
│
└── README.md
```

---

## Documentação

A documentação do projeto encontra-se na pasta `docs`.

### Diagramas UML

* Modelo de Dados (DER)
* Diagrama de Classe
* Diagrama de Caso de Uso
* Diagrama de Atividade

### Requisitos

* Requisitos Funcionais
* Regras de Negócio

### SQL

Consultas desenvolvidas para a Parte 02 do teste técnico.

---

## Funcionalidades

* Cadastro de Empresa Pessoa Jurídica
* Cadastro de Empresa Pessoa Física
* Cadastro de Empresa Estrangeira
* Upload de Documentos
* Validação de CPF
* Validação de CNPJ
* Aprovação de Empresas
* Reprovação de Empresas
* Vinculação de Responsável
* Consulta de Status

---

## Executando o Backend

```bash
cd backend/company-registration

mvnw.cmd spring-boot:run
```

A aplicação estará disponível em:

```text
http://localhost:8080
```

---

## Executando o Frontend

```bash
cd frontend

npm install

npm run dev
```

A aplicação estará disponível em:

```text
http://localhost:5173
```

---

## Autor

Jonas Santos dos Santos

Prova Técnica - Super Terminais
