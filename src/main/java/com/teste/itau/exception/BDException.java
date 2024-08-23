package com.teste.itau.exception;

import org.springframework.dao.DataAccessException;

public class BDException extends RuntimeException {
    public BDException(String mensagem, Throwable cause) {
        super(mensagem);
    }
}
