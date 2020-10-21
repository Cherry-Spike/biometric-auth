package com.cherry.spike.biometric.auth.repository;

import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImpDigitalRepositorio  extends JpaRepository<ImpressaoDigital, Long> {
}
