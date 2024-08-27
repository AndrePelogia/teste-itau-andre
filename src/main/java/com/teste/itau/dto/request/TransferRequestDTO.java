package com.teste.itau.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDTO {

    private String contaOrigem;
    private String contaDestino;
    private Double valor;

}
