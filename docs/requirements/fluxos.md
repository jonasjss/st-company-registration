# Fluxos do Sistema

## Fluxo Principal - Cadastro de Pessoa Jurídica

1. O usuário acessa o menu de Cadastro de Empresa.
2. O sistema exibe a interface de cadastro.
3. O usuário seleciona o tipo de pessoa como Pessoa Jurídica.
4. O usuário informa Razão Social, CNPJ, Nome Fantasia e Perfil.
5. O usuário habilita Faturamento Direto, se desejar.
6. O usuário anexa o documento comprobatório obrigatório.
7. O usuário aciona a opção de adicionar e salva o cadastro.
8. O sistema valida os dados informados.
9. O sistema registra o cadastro da empresa.
10. O sistema exibe mensagem de sucesso.
11. O sistema redireciona o usuário conforme o fluxo executado.
12. Caso o cadastro seja realizado por usuário externo, a empresa permanece aguardando aprovação.

## Fluxo Alternativo FA01 - Cadastro de Pessoa Física

1. O usuário seleciona o tipo de pessoa como Pessoa Física.
2. O usuário informa Nome, CPF, Nome Fantasia e Perfil.
3. O usuário informa os demais campos, se desejar.
4. O usuário habilita Faturamento Direto, se desejar.
5. O usuário anexa o documento comprobatório obrigatório.
6. O usuário salva o cadastro.
7. O sistema valida os dados informados.
8. O sistema registra o cadastro da empresa.
9. O sistema exibe mensagem de sucesso.
10. O sistema redireciona o usuário conforme o fluxo executado.

## Fluxo Alternativo FA02 - Cadastro de Pessoa Estrangeira

1. O usuário seleciona o tipo de pessoa como Pessoa Estrangeira.
2. O usuário informa Razão Social, Identificador Estrangeiro, Nome Fantasia e Perfil.
3. O usuário informa os demais campos, se desejar.
4. O usuário habilita Faturamento Direto, se desejar.
5. O usuário anexa o documento comprobatório obrigatório.
6. O usuário salva o cadastro.
7. O sistema valida os dados informados.
8. O sistema registra o cadastro da empresa.
9. O sistema exibe mensagem de sucesso.
10. O sistema redireciona o usuário conforme o fluxo executado.

## Fluxo Alternativo FA03 - Cadastro por Usuário Interno

1. O usuário interno acessa Administração de Empresas.
2. O sistema exibe a listagem de empresas.
3. O usuário interno aciona a opção de adicionar novo cadastro.
4. O sistema exibe a interface de cadastro de empresa.
5. O usuário interno realiza o cadastro conforme o tipo de pessoa selecionado.
6. O sistema registra a empresa.
7. O cadastro é aprovado automaticamente.
8. O sistema redireciona o usuário interno para a listagem de empresas.

## Fluxos de Exceção

### FE01 - Campo com valor abaixo do mínimo

O sistema deverá exibir mensagem informando a quantidade mínima de caracteres exigida.

### FE02 - CNPJ inválido

O sistema deverá exibir mensagem informando que o CNPJ é inválido.

### FE03 - CPF inválido

O sistema deverá exibir mensagem informando que o CPF é inválido.

### FE04 - Perfil não informado

O sistema deverá exibir mensagem solicitando a seleção de um perfil.

### FE05 - Perfil não encontrado

O sistema deverá exibir mensagem informando erro ao encontrar o perfil.

### FE06 - Documento obrigatório não anexado

O sistema deverá exibir mensagem informando que é necessário enviar os arquivos obrigatórios.

### FE07 - Formato de arquivo inválido

O sistema deverá permitir apenas arquivos PDF, PNG, JPG e JPEG.

### FE08 - Arquivo duplicado

O sistema deverá exibir mensagem informando que o arquivo está duplicado.
