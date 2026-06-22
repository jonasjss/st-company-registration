package com.superterminais.companyregistration.repository;

import com.superterminais.companyregistration.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}