package com.cherry.spike.biometric.auth.model.entidade;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tbl_ImpressaoDigital")
@NoArgsConstructor
@AllArgsConstructor()
@RequiredArgsConstructor(staticName = "novo")
public class ImpressaoDigital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Digital")
    @Getter
    private long id;
    @Getter
    @NonNull
    @Column(name = "Nome", nullable = false)
    private String nome;
    @Getter
    @NonNull
    @Column(name = "TipoArquivo", nullable = false)
    private String tipoArquivo;
    @Getter
    @NonNull
    @Lob
    @Column(name = "Conteudo", nullable = false)
    private byte[] conteudo;
    @Getter
    @NonNull
    @OneToOne(optional = false)
    @JoinColumn(name = "Usuario_ID")
    private Usuario usuario;
}
