package com.cherry.spike.biometric.auth.controller;

import com.cherry.spike.biometric.auth.model.Reposta;
import com.cherry.spike.biometric.auth.model.excecoes.UsuarioNaoEncontradoException;
import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import com.cherry.spike.biometric.auth.model.dtos.impressaoDigitalDTO;
import com.cherry.spike.biometric.auth.service.ImpressaoDigitalServico;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class ImpressaoDigitalController {
    private final ImpressaoDigitalServico impressaoDigitalServico;
    private final static String IMPRESSAO_DIGITAL = "/impressaoDigital";
    private final static String USUARIO_ID = "usuarioId";
    private final static String ARQUIVO = "arquivo";
    private final static String SALVAR_DIGITAL = IMPRESSAO_DIGITAL+"/{usuarioId}/arquivo";

    public ImpressaoDigitalController(ImpressaoDigitalServico impressaoDigitalServico) {
        this.impressaoDigitalServico = impressaoDigitalServico;
    }

    @PostMapping(value = SALVAR_DIGITAL, consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Reposta<impressaoDigitalDTO>> salvar(@RequestParam(ARQUIVO) MultipartFile arquivo, @PathVariable(USUARIO_ID) long usuarioId) {
        Reposta<impressaoDigitalDTO> resposta = new Reposta<>();
        try {
            Optional<ImpressaoDigital> impressaoDigital = impressaoDigitalServico.salvar(arquivo, usuarioId);
            if (!impressaoDigital.isPresent()){
                resposta.setConteudo(new impressaoDigitalDTO());
                new ResponseEntity<>(resposta, HttpStatus.BAD_REQUEST);
            }
            impressaoDigitalDTO repostaDTO = impressaoDigitalDTO.converterEntidadeParaDTO(impressaoDigital.get());
            resposta.setConteudo(repostaDTO);
            return new ResponseEntity<>(resposta, HttpStatus.CREATED);
        }catch (UsuarioNaoEncontradoException naoEncontrado){
            resposta.adicionarMensagemErro(naoEncontrado.getMessage());
            resposta.setConteudo(new impressaoDigitalDTO());
            return new ResponseEntity<>(resposta, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            resposta.adicionarMensagemErro(e.getMessage());
            resposta.setConteudo(new impressaoDigitalDTO());
            return new ResponseEntity<>(resposta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}