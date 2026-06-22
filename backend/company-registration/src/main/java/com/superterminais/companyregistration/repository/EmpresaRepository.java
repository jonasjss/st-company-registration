package com.superterminais.companyregistration.repository;

import com.superterminais.companyregistration.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}