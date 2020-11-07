package com.cherry.spike.biometric.auth.controller;

import com.cherry.spike.biometric.auth.model.Reposta;
import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.model.excecoes.UsuarioNaoEncontradoException;
import com.cherry.spike.biometric.auth.service.CargoServico;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:8080")
public class CargoController {
    private static final String CARGOS = "/cargos";
    private final CargoServico cargoServico;

    public CargoController(CargoServico cargoServico) {
        this.cargoServico = cargoServico;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = CARGOS, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<List<Cargo>>> obterTodos() {
        Reposta<List<Cargo>> reposta = new Reposta<>();
        try {
            List<Cargo> cargos = cargoServico.obterTodos();
            reposta.setConteudo(cargos);
            return new ResponseEntity<>(reposta, HttpStatus.OK);
        }catch (UsuarioNaoEncontradoException naoEncontrado){
            reposta.adicionarMensagemErro(naoEncontrado.getMessage());
            return new ResponseEntity<>(reposta, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
