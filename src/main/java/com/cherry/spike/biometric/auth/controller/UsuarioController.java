package com.cherry.spike.biometric.auth.controller;

import com.cherry.spike.biometric.auth.model.*;
import com.cherry.spike.biometric.auth.model.dtos.UsuarioDTO;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.model.excecoes.CargoNaoEncontradoException;
import com.cherry.spike.biometric.auth.service.UsuarioServico;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class UsuarioController {
    private static final String USUARIO = "/usuario";
    private static final String USUARIO_POR_ID = USUARIO + "/{id}";
    private final UsuarioServico usuarioServico;

    public UsuarioController(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    @PostMapping(value = USUARIO, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<UsuarioDTO>> salvar(@RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        Reposta<UsuarioDTO> reposta = new Reposta<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error
                    -> reposta.adicionarMensagemErro(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(reposta);
        }
        try {
            Optional<Usuario> usuario = usuarioServico.salvar(usuarioDTO);
            if (!usuario.isPresent()){
                reposta.setConteudo(new UsuarioDTO());
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }

            UsuarioDTO repostaDTO = UsuarioDTO.converterEntidadeParaDTO(usuario.get());
            reposta.setConteudo(repostaDTO);
            return new ResponseEntity<>(reposta, HttpStatus.CREATED);
        }catch (CargoNaoEncontradoException naoEncontrado){
            reposta.adicionarMensagemErro(naoEncontrado.getMessage());
            reposta.setConteudo(new UsuarioDTO());
            return new ResponseEntity<>(reposta, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            reposta.setConteudo(new UsuarioDTO());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = USUARIO_POR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<UsuarioDTO>> obterPorId(@PathVariable("id") long id) {
        Reposta<UsuarioDTO> reposta = new Reposta<>();
        try {
            Optional<Usuario> usuario = usuarioServico.obterPorId(id);
            if (!usuario.isPresent()){
                reposta.setConteudo(new UsuarioDTO());
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }

            UsuarioDTO responseDTO = UsuarioDTO.converterEntidadeParaDTO(usuario.get());
            reposta.setConteudo(responseDTO);
            return new ResponseEntity<>(reposta, HttpStatus.CREATED);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            reposta.setConteudo(new UsuarioDTO());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
