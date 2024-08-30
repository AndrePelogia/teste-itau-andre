package com.teste.itau.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Entity
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String numeroContaOrigem;
    private String numeroContaDestino;
    private Double valor;
    private Boolean sucesso;
    private LocalDateTime dataTransferencia;
}
