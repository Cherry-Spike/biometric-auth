package com.cherry.spike.biometric.auth.model.entidade;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class Nivel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_Nivel", nullable=false)
    private long id;
    @Column(name="Descricao", nullable=false)
    private String descricao;
}

