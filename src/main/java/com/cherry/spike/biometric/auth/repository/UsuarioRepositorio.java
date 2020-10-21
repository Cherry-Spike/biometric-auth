package com.cherry.spike.biometric.auth.repository;

import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
