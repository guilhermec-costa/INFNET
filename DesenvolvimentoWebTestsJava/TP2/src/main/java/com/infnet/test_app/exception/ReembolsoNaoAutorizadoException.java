package com.infnet.test_app.exception;

public class ReembolsoNaoAutorizadoException extends RuntimeException {
    public ReembolsoNaoAutorizadoException(String message) {
        super(message);
    }
}
