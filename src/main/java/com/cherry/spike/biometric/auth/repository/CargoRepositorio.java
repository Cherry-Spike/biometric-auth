package com.cherry.spike.biometric.auth.repository;

import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CargoRepositorio extends JpaRepository<Cargo, Long> {
    public Cargo getById(long cargoId);
}
