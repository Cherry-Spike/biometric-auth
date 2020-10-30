package com.cherry.spike.biometric.auth.repository;

import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImpDigitalRepositorio  extends JpaRepository<ImpressaoDigital, Long> {
    Optional<ImpressaoDigital> findByUsuario(Usuario usuario);
}
