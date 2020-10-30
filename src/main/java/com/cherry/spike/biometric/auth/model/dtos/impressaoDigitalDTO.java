package com.cherry.spike.biometric.auth.model.dtos;

import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

public class impressaoDigitalDTO {
    @Getter @Setter
    private long id;
    @Getter @Setter
    private String nome;
    @Getter @Setter
    private String tipoArquivo;
    @Getter @Setter
    private long usuarioId;

    public static impressaoDigitalDTO converterEntidadeParaDTO(ImpressaoDigital impressaoDigital) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(ImpressaoDigital.class, impressaoDigitalDTO.class)
                .addMapping( src -> src.getUsuario().getId(), impressaoDigitalDTO::setUsuarioId);
        return modelMapper.map(impressaoDigital, impressaoDigitalDTO.class);
    }
}
