package com.cherry.spike.biometric.auth.controller;

import com.cherry.spike.biometric.auth.model.*;
import com.cherry.spike.biometric.auth.model.dtos.UsuarioDTO;
import com.cherry.spike.biometric.auth.model.dtos.UsuarioRespostaDTO;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.model.excecoes.CargoNaoEncontradoException;
import com.cherry.spike.biometric.auth.model.excecoes.UsuarioNaoEncontradoException;
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
@CrossOrigin(origins = "http://localhost:8080")
public class UsuarioController {
    private static final String USUARIO = "/usuario";
    private static final String USUARIO_POR_ID = USUARIO + "/{id}";
    private final UsuarioServico usuarioServico;

    public UsuarioController(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    @PostMapping(value = USUARIO, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<UsuarioRespostaDTO>> salvar(@RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        Reposta<UsuarioRespostaDTO> reposta = new Reposta<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error
                    -> reposta.adicionarMensagemErro(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(reposta);
        }
        try {
            Optional<Usuario> usuario = usuarioServico.salvar(usuarioDTO);
            if (!usuario.isPresent()){
                reposta.setConteudo(new UsuarioRespostaDTO());
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }

            UsuarioRespostaDTO repostaDTO = UsuarioRespostaDTO.converterEntidadeParaDTO(usuario.get());
            reposta.setConteudo(repostaDTO);
            return new ResponseEntity<>(reposta, HttpStatus.CREATED);
        }catch (CargoNaoEncontradoException naoEncontrado){
            reposta.adicionarMensagemErro(naoEncontrado.getMessage());
            reposta.setConteudo(new UsuarioRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            reposta.setConteudo(new UsuarioRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = USUARIO, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<UsuarioRespostaDTO>> atualizar (@RequestBody UsuarioDTO usuarioDTO, BindingResult result) {
        Reposta<UsuarioRespostaDTO> reposta = new Reposta<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error
                    -> reposta.adicionarMensagemErro(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(reposta);
        }
        try {
            Optional<Usuario> usuario = usuarioServico.atualizar(usuarioDTO);
            if (!usuario.isPresent()){
                reposta.setConteudo(new UsuarioRespostaDTO());
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }

            UsuarioRespostaDTO repostaDTO = UsuarioRespostaDTO.converterEntidadeParaDTO(usuario.get());
            reposta.setConteudo(repostaDTO);
            return new ResponseEntity<>(reposta, HttpStatus.OK);
        }catch (CargoNaoEncontradoException naoEncontrado){
            reposta.adicionarMensagemErro(naoEncontrado.getMessage());
            reposta.setConteudo(new UsuarioRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            reposta.setConteudo(new UsuarioRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = USUARIO_POR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<UsuarioRespostaDTO>> obterPorId(@PathVariable("id") long id) {
        Reposta<UsuarioRespostaDTO> reposta = new Reposta<>();
        try {
            Optional<Usuario> usuario = usuarioServico.obterPorId(id);
            if (!usuario.isPresent()){
                reposta.setConteudo(new UsuarioRespostaDTO());
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }

            UsuarioRespostaDTO responseDTO = UsuarioRespostaDTO.converterEntidadeParaDTO(usuario.get());
            reposta.setConteudo(responseDTO);
            return new ResponseEntity<>(reposta, HttpStatus.OK);
        }catch (UsuarioNaoEncontradoException naoEncontrado){
            reposta.adicionarMensagemErro(naoEncontrado.getMessage());
            return new ResponseEntity<>(reposta, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            reposta.setConteudo(new UsuarioRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = USUARIO_POR_ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<Boolean>> delete(@PathVariable("id") long id) {
        Reposta<Boolean> reposta = new Reposta<>();
        try {
            usuarioServico.delete(id);
            reposta.setConteudo(true);
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
