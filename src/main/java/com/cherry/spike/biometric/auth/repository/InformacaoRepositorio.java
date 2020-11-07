package com.cherry.spike.biometric.auth.repository;

import com.cherry.spike.biometric.auth.model.entidade.Informacao;
import com.cherry.spike.biometric.auth.model.entidade.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InformacaoRepositorio extends JpaRepository<Informacao, Long> {
    Optional<List<Informacao>> findAllByNivel(Nivel nivel);
}
