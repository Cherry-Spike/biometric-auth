package com.cherry.spike.biometric.auth.model.dtos;

import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
@Getter @Setter
public class ImpressaoDigitalDTO {
    private String nome;
    private String tipoArquivo;
    private long usuarioId;
}
