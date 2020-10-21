package com.cherry.spike.biometric.auth.model.entidade;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private long id;
    @NotNull
    @Getter @Setter
    private String nome;
    @NotNull
    @Getter @Setter
    private String sobrenome;
    @NotNull
    @Getter @Setter
    private long cargoId;
    @NotNull
    @Getter @Setter
    private String nomeUsuario;
    @OneToOne
    @NotNull
    @Getter @Setter
    private ImpressaoDigital impDigital;

    public Usuario(String nome, String sobrenome, long cargoId, String nomeUsuario, ImpressaoDigital impDigital) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cargoId = cargoId;
        this.nomeUsuario = nomeUsuario;
        this.impDigital = impDigital;
    }
}
