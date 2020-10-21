package com.cherry.spike.biometric.auth.service;

import com.cherry.spike.biometric.auth.model.CargoInvalidoException;
import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.model.enums.Cargos;
import com.cherry.spike.biometric.auth.repository.CargoRepositorio;
import com.cherry.spike.biometric.auth.repository.ImpDigitalRepositorio;
import com.cherry.spike.biometric.auth.repository.UsuarioRepositorio;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class UsuarioServicoTeste {

    @MockBean
    UsuarioRepositorio usuarioRepositorio;
    @MockBean
    CargoRepositorio cargoRepositorio;
    @MockBean
    ImpDigitalRepositorio impDigitalRepositorio;


    @InjectMocks
    UsuarioServico usuarioServico;

    @Test
    public void quando_salvar_usuario_deve_retornar_usuario() {
        String nome = "Everton";
        String sobrenome = "Ferreira";
        long cargoId = Cargos.COLABORADOR.ordinal();
        String nomeUsuario = "Everton42";
        ImpressaoDigital impDigital = new ImpressaoDigital(new byte[7]);
        Usuario usuario = new Usuario(nome, sobrenome, cargoId, nomeUsuario , impDigital);

        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);
        when(cargoRepositorio.getById(usuario.getCargoId())).thenReturn(new Cargo());

        Usuario resultado = usuarioServico.salvar(usuario);

        assertEquals(usuario, resultado);
    }

    @Test
    public void quando_salvar_usuario_cargo_invalido_deve_retornar_excecao(){
        String nome = "Everton";
        String sobrenome = "Ferreira";
        long cargoId = -4;
        String nomeUsuario = "Everton42";
        ImpressaoDigital impDigital = new ImpressaoDigital(new byte[7]);
        Usuario usuario = new Usuario(nome, sobrenome, cargoId, nomeUsuario , impDigital);
        when(usuarioRepositorio.save(usuario)).thenReturn(usuario);
        when(cargoRepositorio.getById(usuario.getCargoId())).thenReturn(null);

        assertThrows(CargoInvalidoException.class, () -> {
            usuarioServico.salvar(usuario);
        });
    }
}
