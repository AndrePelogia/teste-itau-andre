package com.teste.itau.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    @NotBlank(message = "Número da conta é obrigatório")
    private String numeroConta;

    @NotNull(message = "Saldo é obrigatório")
    @Positive(message = "Saldo deve ser positivo")
    private double saldo;
}
