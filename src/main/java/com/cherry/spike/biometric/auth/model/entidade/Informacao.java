package com.cherry.spike.biometric.auth.model.entidade;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "novo")
@Getter
public class Informacao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID_Informacao", nullable=false)
    private long id;
    @NonNull
    @Column(name="Risco", nullable=false)
    private String risco;
    @NonNull
    @OneToOne(optional = false)
    @JoinColumn(name = "Nivel_ID")
    private Nivel nivel;
    @NonNull
    @Column(name="Agrotoxico", nullable=false)
    private String agrotoxico;
    @NonNull
    @Column(name="Endereco", nullable=false)
    private String endereco;
}
