package com.cherry.spike.biometric.auth.model.entidade;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity(name = "tbl_Usuario")
@NoArgsConstructor
@RequiredArgsConstructor(staticName = "novo")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID_Usuario", nullable=false)
    @Getter
    private long id;
    @Column(name="Nome", nullable=false)
    @Getter
    @NonNull
    private String nome;
    @Column(name="Sobrenome", nullable=false)
    @Getter
    @NonNull
    private String sobrenome;
    @ManyToOne()
    @Getter
    @NonNull
    private Cargo cargo;
    @Getter
    @NonNull
    @Column(name="Login", nullable=false)
    private String login;
    @Getter
    @NonNull
    @Column(name="Senha", nullable=false)
    private String senha;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "Impressao_Digital_ID", nullable=true, referencedColumnName = "ID_Digital")
    @Getter
    private ImpressaoDigital impDigital;
}
