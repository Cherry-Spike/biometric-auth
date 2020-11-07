package com.cherry.spike.biometric.auth.service;

import com.cherry.spike.biometric.auth.model.entidade.Cargo;
import com.cherry.spike.biometric.auth.model.excecoes.CargoNaoEncontradoException;
import com.cherry.spike.biometric.auth.model.excecoes.ImpressaoDigitaLNaoEncontradaException;
import com.cherry.spike.biometric.auth.model.excecoes.UsuarioNaoEncontradoException;
import com.cherry.spike.biometric.auth.model.entidade.ImpressaoDigital;
import com.cherry.spike.biometric.auth.model.entidade.Usuario;
import com.cherry.spike.biometric.auth.model.excecoes.UsuarioPossuiImpDigitalException;
import com.cherry.spike.biometric.auth.repository.ImpDigitalRepositorio;
import com.cherry.spike.biometric.auth.repository.UsuarioRepositorio;
import com.machinezoo.sourceafis.FingerprintImage;
import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

@Service
public class ImpressaoDigitalServico {
    private static final String USUARIO_NAO_ENCONTRADO_MENSAGEM = "Usuário não encontrado!";
    private static final double MINIMO_PERCENTUAL = 40;
    private static final String USUARIO_JA_POSSUI_IMPDIGITAL_MENSAGEM = "Usuário já possui impressão digital!";
    private static final String USUARIO_NAO_POSSUI_IMPDIGITAL_MENSAGEM = "Usuário não possui impressão digital!";
    private final ImpDigitalRepositorio impDigitalRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    public ImpressaoDigitalServico(ImpDigitalRepositorio impDigitalRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.impDigitalRepositorio = impDigitalRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    public Optional<ImpressaoDigital> salvar(MultipartFile arquivo, long usuarioId) throws IOException {
        if(!ehUsuarioValido(usuarioId))
            throw new UsuarioNaoEncontradoException(USUARIO_NAO_ENCONTRADO_MENSAGEM);
        Optional<Usuario> usuario = usuarioRepositorio.findById(usuarioId);
        if (!usuario.isPresent())
            throw new UsuarioNaoEncontradoException(USUARIO_NAO_ENCONTRADO_MENSAGEM);

        if(usuarioPossuiImpDigital(usuarioId))
            throw new UsuarioPossuiImpDigitalException(USUARIO_JA_POSSUI_IMPDIGITAL_MENSAGEM);

        String nomeArquivo = StringUtils.cleanPath(arquivo.getOriginalFilename());
        ImpressaoDigital arquivoDb =
            arquivoDb = ImpressaoDigital.novo(nomeArquivo, arquivo.getContentType(), arquivo.getBytes(), usuario.get());

        return Optional.of(impDigitalRepositorio.save(arquivoDb));
    }

    private boolean ehUsuarioValido(long usuarioId) {
        return usuarioRepositorio.findById(usuarioId).isPresent();
    }

    public boolean ehDigitalValida(String login, MultipartFile arquivo) throws IOException {
        double porcentagem = 0;
        try{
            Optional<Usuario> usuario = usuarioRepositorio.findByLogin(login);
            if (!usuario.isPresent())
                throw new UsuarioNaoEncontradoException(MessageFormat.format("Usuario com login {1}, não encontrado",login));
            Optional<ImpressaoDigital> impressaoDigital = impDigitalRepositorio.findByUsuarioId(usuario.get().getId());

            FingerprintTemplate impressaoDigitalBd = new FingerprintTemplate(
                    new FingerprintImage()
                            .dpi(500)
                            .decode(impressaoDigital.get().getConteudo()));

            FingerprintTemplate impressaoDigitalEmTeste = null;
                impressaoDigitalEmTeste = new FingerprintTemplate(
                        new FingerprintImage()
                                .dpi(500)
                                .decode(arquivo.getBytes()));

            porcentagem = new FingerprintMatcher()
                    .index(impressaoDigitalBd)
                    .match(impressaoDigitalEmTeste);
        } catch (IOException e) {
            throw new IOException("Erro ao ler arquivo "+e.getMessage());
        }
        return porcentagem >= MINIMO_PERCENTUAL;
    }

    public Optional<ImpressaoDigital> atualizar(MultipartFile arquivo, long usuarioId) throws IOException {
        Optional<ImpressaoDigital> impDigital = impDigitalRepositorio.findByUsuarioId(usuarioId);
        if(!impDigital.isPresent())
            throw new ImpressaoDigitaLNaoEncontradaException(USUARIO_NAO_POSSUI_IMPDIGITAL_MENSAGEM);

        impDigital.get().setConteudo(arquivo.getBytes());
        impDigital.get().setNome(arquivo.getName());
        impDigital.get().setTipoArquivo(arquivo.getContentType());

        return Optional.of(impDigitalRepositorio.save(impDigital.get()));
    }

    private boolean usuarioPossuiImpDigital(long usuarioId) {
        return impDigitalRepositorio.findByUsuarioId(usuarioId).isPresent();
    }
}
