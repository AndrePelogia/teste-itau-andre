package com.teste.itau.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferRequestDTO {

    @NotBlank(message = "Número da conta origem é obrigatório")
    private String contaOrigem;

    @NotBlank(message = "Número da conta destino é obrigatório")
    private String contaDestino;

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "Valor deve ser positivo")
    private Double valor;

}
