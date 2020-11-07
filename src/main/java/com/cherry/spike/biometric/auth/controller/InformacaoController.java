package com.cherry.spike.biometric.auth.controller;

import com.cherry.spike.biometric.auth.model.Reposta;
import com.cherry.spike.biometric.auth.model.dtos.InformacaoDTO;
import com.cherry.spike.biometric.auth.model.dtos.InformacaoRespostaDTO;
import com.cherry.spike.biometric.auth.model.dtos.UsuarioDTO;
import com.cherry.spike.biometric.auth.model.dtos.UsuarioRespostaDTO;
import com.cherry.spike.biometric.auth.model.entidade.Informacao;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.model.excecoes.CargoNaoEncontradoException;
import com.cherry.spike.biometric.auth.model.excecoes.NivelInvalidoException;
import com.cherry.spike.biometric.auth.model.excecoes.UsuarioNaoEncontradoException;
import com.cherry.spike.biometric.auth.service.InformacaoServico;
import com.cherry.spike.biometric.auth.service.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
@CrossOrigin(origins = "http://localhost:8080")
public class InformacaoController {
    private static final String INFORMACAO = "/informacao";
    private static final String INFORMACAO_POR_USUARIO = INFORMACAO + "/{usuarioId}";
    private final InformacaoServico informacaoServico;

    public InformacaoController(InformacaoServico informacaoServico) {
        this.informacaoServico = informacaoServico;
    }

    @PostMapping(value = INFORMACAO, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<InformacaoRespostaDTO>> salvar(@RequestBody InformacaoDTO informacaoDTO, BindingResult result) {
        Reposta<InformacaoRespostaDTO> reposta = new Reposta<>();
        if (result.hasErrors()) {
            result.getAllErrors().forEach(error
                    -> reposta.adicionarMensagemErro(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(reposta);
        }
        try {
            Optional<Informacao> informacao = informacaoServico.salvar(informacaoDTO);
            if (!informacao.isPresent()){
                reposta.setConteudo(new InformacaoRespostaDTO());
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }
            InformacaoRespostaDTO repostaDTO = InformacaoRespostaDTO.converterEntidadeParaDTO(informacao.get());
            reposta.setConteudo(repostaDTO);
            return new ResponseEntity<>(reposta, HttpStatus.CREATED);
        }catch (NivelInvalidoException naoEncontrado){
            reposta.adicionarMensagemErro(naoEncontrado.getMessage());
            reposta.setConteudo(new InformacaoRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            reposta.adicionarMensagemErro(e.getMessage());
            reposta.setConteudo(new InformacaoRespostaDTO());
            return new ResponseEntity<>(reposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Secured("ROLE_ADMIN")
    @GetMapping(value = INFORMACAO_POR_USUARIO, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<List<InformacaoRespostaDTO>>> obterPorUsuarioId(@PathVariable("usuarioId") long usuarioId) {
        Reposta<List<InformacaoRespostaDTO>> reposta = new Reposta<>();
        try {
            Optional<List<Informacao>> informacoes = informacaoServico.obterPorUsuarioId(usuarioId);
            if (!informacoes.isPresent()){
                new ResponseEntity<>(reposta, HttpStatus.BAD_REQUEST);
            }
            List<InformacaoRespostaDTO> infos = informacoes.get()
                    .stream().map(informacao ->
                    InformacaoRespostaDTO.converterEntidadeParaDTO(informacao)).collect(Collectors.toList());

            reposta.setConteudo(infos);
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
