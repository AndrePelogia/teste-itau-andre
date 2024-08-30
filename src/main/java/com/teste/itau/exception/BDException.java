package com.teste.itau.exception;

public class BDException extends RuntimeException {
    public BDException(String mensagem, Throwable cause) {
        super(mensagem);
    }
}
