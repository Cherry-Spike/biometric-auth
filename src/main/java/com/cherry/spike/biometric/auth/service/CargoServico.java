package com.cherry.spike.biometric.auth.service;

import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.repository.CargoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoServico {
    @Autowired
    CargoRepositorio cargoRepositorio;

    public List<Cargo> obterTodos() {
        return cargoRepositorio.findAll();
    }
}
