-- =====================================================
-- PARTE 02 - TESTE TÉCNICO SUPER TERMINAIS
-- Consultas SQL Oracle
-- Autor: Jonas Santos dos Santos
-- =====================================================

-- a) Listar todos os funcionários com os nomes dos cargos e departamentos
SELECT
    f.Nome_Func,
    c.Nome_CAR_Cargo,
    d.Nome_DEP_Depto
FROM FUNCIONARIOS f
INNER JOIN CARGOS c
    ON f.Id_FUN_Cargo = c.Id_CAR_Cargo
INNER JOIN DEPARTAMENTOS d
    ON f.Id_FUN_Depto = d.Id_DEP_Depto;

-- b) Listar todos os funcionários que estão ativos na empresa
SELECT
    *
FROM FUNCIONARIOS
WHERE data_demis IS NULL;

-- c) Listar todos os funcionários que trabalham na área de Controladoria
SELECT
    f.Nome_Func,
    c.Nome_CAR_Cargo,
    d.Nome_DEP_Depto
FROM FUNCIONARIOS f
INNER JOIN CARGOS c
    ON f.Id_FUN_Cargo = c.Id_CAR_Cargo
INNER JOIN DEPARTAMENTOS d
    ON f.Id_FUN_Depto = d.Id_DEP_Depto
WHERE UPPER(d.Nome_DEP_Depto) = 'CONTROLADORIA';

-- d) Listar todos os funcionários onde o salário seja superior a R$ 2.900,00
SELECT *
FROM FUNCIONARIOS
WHERE Salario > 2900;

-- e) Listar a quantidade de pessoas em cada departamento
SELECT
    d.Nome_DEP_Depto,
    COUNT(f.ID_Func) AS Quantidade_Pessoas
FROM DEPARTAMENTOS d
LEFT JOIN FUNCIONARIOS f
    ON f.Id_FUN_Depto = d.Id_DEP_Depto
GROUP BY d.Nome_DEP_Depto
ORDER BY d.Nome_DEP_Depto;