package com.cherry.spike.biometric.auth.model.entidade;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "tbl_Cargo")
@AllArgsConstructor
@NoArgsConstructor
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_Cargo", nullable=false)
    @Getter @Setter
    private long id;
    @Column(name="Descricao", nullable=false)
    @Getter @Setter
    private String descricao;
    @Getter @Setter
    @ManyToOne()
    @JoinColumn(name = "Nivel_ID", referencedColumnName = "ID_Nivel")
    private Nivel nivel;
}
