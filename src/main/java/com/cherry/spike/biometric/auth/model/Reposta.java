package com.cherry.spike.biometric.auth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reposta<T> {

    private T conteudo;
    private Object erros;

    public void adicionarMensagemErro(String msgError) {
        RespostaComErro error = new RespostaComErro()
                .setDetalhes(msgError)
                .setHorario(LocalDateTime.now());
        setErros(error);
    }

    @Getter
    @Setter
    @Accessors(chain = true)
    @NoArgsConstructor
    public class RespostaComErro {

        @NotNull(message="Horário não pode ser nulo")
        private LocalDateTime horario;

        @NotNull(message="Detalhes não pode ser nulo")
        private String detalhes;
    }
}
