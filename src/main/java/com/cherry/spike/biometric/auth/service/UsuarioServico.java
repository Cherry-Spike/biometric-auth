package com.cherry.spike.biometric.auth.service;

import com.cherry.spike.biometric.auth.model.dtos.UsuarioDTO;
import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.model.excecoes.CargoNaoEncontradoException;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.repository.CargoRepositorio;
import com.cherry.spike.biometric.auth.repository.UsuarioRepositorio;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioServico {

    private final UsuarioRepositorio usuarioRepositorio;
    private final CargoRepositorio cargoRepositorio;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServico(UsuarioRepositorio usuarioRepositorio, CargoRepositorio cargoRepositorio, PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.cargoRepositorio = cargoRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<Usuario> salvar(UsuarioDTO usuarioDTO) {
        if(!ehCargoValido(usuarioDTO.getCargoId()))
            throw new CargoNaoEncontradoException("Cargo não encontrado!");

        String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
        Optional<Cargo> cargo = cargoRepositorio.findById(usuarioDTO.getCargoId());

        if(!cargo.isPresent() && senhaCriptografada.isBlank())
            return Optional.empty();

        Usuario usuario = Usuario
                .novo(usuarioDTO.getNome(), usuarioDTO.getSobrenome(), cargo.get(), usuarioDTO.getLogin(), senhaCriptografada);

        return Optional.of(usuarioRepositorio.save(usuario));
    }

    public Optional<Usuario> findById(long usuarioId) {
        return usuarioRepositorio.findById(usuarioId);
    }

    public Optional<Usuario> findByLogin(String login) {
        return usuarioRepositorio.findByLogin(login);
    }

    public Optional<Usuario> obterPorId(long id) {
        Optional<Usuario> usuario = usuarioRepositorio.findById(id);
        if (usuario.isPresent())
            Optional.of(usuario);
        return usuario;
    }

    private boolean ehCargoValido(long cargoId) {
        Optional<Cargo> cargo = cargoRepositorio.findById(cargoId);
        return cargo.isPresent();
    }
}
