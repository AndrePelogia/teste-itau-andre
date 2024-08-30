package com.teste.itau.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransferResponseDTO {
    private String numeroContaOrigem;
    private String numeroContaDestino;
    private Double valor;
    private String sucesso;
    private LocalDateTime dataTransferencia;
}
