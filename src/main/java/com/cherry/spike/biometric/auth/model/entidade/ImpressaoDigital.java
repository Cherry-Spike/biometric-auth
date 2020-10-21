package com.cherry.spike.biometric.auth.model.entidade;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
public class ImpressaoDigital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;
    @NotNull
    @Getter @Setter
    private byte[] imagem;

    public ImpressaoDigital(byte[] imagem) {
        this.imagem = imagem;
    }
}
