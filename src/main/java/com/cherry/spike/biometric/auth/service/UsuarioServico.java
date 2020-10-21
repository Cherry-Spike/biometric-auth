package com.cherry.spike.biometric.auth.service;

import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.model.CargoInvalidoException;
import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.repository.CargoRepositorio;
import com.cherry.spike.biometric.auth.repository.ImpDigitalRepositorio;
import com.cherry.spike.biometric.auth.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServico {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;
    @Autowired
    private CargoRepositorio cargoRepositorio;
    @Autowired
    private ImpDigitalRepositorio impDigitalRepositorio;

    public Usuario salvar(Usuario usuario) {
        if(!ehCargoValido(usuario.getCargoId()))
            throw new CargoInvalidoException("Cargo não encontrado!");

        ImpressaoDigital impDigital = impDigitalRepositorio.save(usuario.getImpDigital());

        return usuarioRepositorio.save(usuario);
    }

    private boolean ehCargoValido(long cargoId) {
        Cargo cargo = cargoRepositorio.getById(cargoId);
        return cargo != null;
    }
}
