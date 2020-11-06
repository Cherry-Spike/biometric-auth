package com.cherry.spike.biometric.auth.model.dtos;

import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import lombok.*;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private long id;
    private String nome;
    private String sobrenome;
    private long cargoId;
    private String login;
    private String senha;

    public static Usuario converterDTOparaEntidade(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }
}
