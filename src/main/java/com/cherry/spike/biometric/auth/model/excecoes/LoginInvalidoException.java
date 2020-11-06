package com.cherry.spike.biometric.auth.model.excecoes;

public class LoginInvalidoException extends RuntimeException {
    public LoginInvalidoException(String mensagem) {
        super(mensagem);
    }
}
