package com.teste.itau.exception;

public class CustomApiException extends RuntimeException{
    public CustomApiException(String mensagem) {
        super(mensagem);
    }
}
