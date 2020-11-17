package com.cherry.spike.biometric.auth.repository;

import com.cherry.spike.biometric.auth.model.entidade.Informacao;
import com.cherry.spike.biometric.auth.model.entidade.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InformacaoRepositorio extends JpaRepository<Informacao, Long> {
    @Query("SELECT i FROM Informacao i WHERE i.nivel.id <= :nivel")
    Optional<List<Informacao>> findByNivel(@Param("nivel") Nivel nivel);
}
