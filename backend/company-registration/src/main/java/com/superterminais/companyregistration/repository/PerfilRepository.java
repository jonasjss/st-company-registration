package com.superterminais.companyregistration.repository;

import com.superterminais.companyregistration.entity.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}