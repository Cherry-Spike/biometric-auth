package com.cherry.spike.biometric.auth.model.dtos;

import com.cherry.spike.biometric.auth.model.entidade.Informacao;
import lombok.*;
import org.modelmapper.ModelMapper;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor()
@Getter @Setter
public class InformacaoRespostaDTO {
    private String descricao;
    private long nivelId;
    private String nivelDescricao;
    private String agrotoxico;
    private String endereco;

    public static InformacaoRespostaDTO converterEntidadeParaDTO(Informacao informacao) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Informacao.class, InformacaoRespostaDTO.class)
                .addMapping( src -> src.getNivel().getId(), InformacaoRespostaDTO::setNivelId);
        modelMapper.typeMap(Informacao.class, InformacaoRespostaDTO.class)
                .addMapping( src -> src.getNivel().getDescricao(), InformacaoRespostaDTO::setNivelDescricao);
        return modelMapper.map(informacao, InformacaoRespostaDTO.class);
    }
}
