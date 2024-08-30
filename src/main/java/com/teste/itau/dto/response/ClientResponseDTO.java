package com.teste.itau.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ClientResponseDTO {
    private UUID id;
    private String nome;
    private String numeroConta;
    private double saldo;
}
