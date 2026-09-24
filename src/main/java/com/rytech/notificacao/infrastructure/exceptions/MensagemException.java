package com.rytech.notificacao.infrastructure.exceptions;

public class MensagemException extends RuntimeException {

    public MensagemException(String message) {
        super(message);
    }

    public MensagemException(String mensagem, Throwable throwable) {
        super(mensagem, throwable);
    }
}
