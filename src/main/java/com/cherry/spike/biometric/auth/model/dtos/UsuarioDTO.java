package com.cherry.spike.biometric.auth.model.dtos;

import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private String nome;
    private String sobrenome;
    private long cargoId;
    private String login;
    @JsonIgnore
    private String senha;

    public static Usuario converterDTOparaEntidade(UsuarioDTO dto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(dto, Usuario.class);
    }

    public static UsuarioDTO converterEntidadeParaDTO(Usuario usuario) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.typeMap(Usuario.class, UsuarioDTO.class)
                .addMapping( src -> src.getCargo().getId(), UsuarioDTO::setCargoId);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

}
