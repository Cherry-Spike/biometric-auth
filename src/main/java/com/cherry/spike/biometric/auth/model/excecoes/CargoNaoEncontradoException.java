package com.cherry.spike.biometric.auth.model.excecoes;

public class CargoNaoEncontradoException extends RuntimeException {
    public CargoNaoEncontradoException(String message){
        super(message);
    }
}
