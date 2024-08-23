package com.teste.itau.controller;

import com.teste.itau.dto.ManagementClientRequestDTO;
import com.teste.itau.service.ManagementClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/teste")
public class ManagementClientController {
    @Autowired
    private ManagementClientService managementClientService;
    @Operation(summary = "Cadastrar novo cliente",
            description = "Adiciona um novo cliente com ID, nome, número de conta e saldo.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Cliente cadastrado com sucesso",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ManagementClientRequestDTO.class))
            }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = @Content)
    })
    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<Map<String, String>> cadastrarCliente(@Valid @RequestBody ManagementClientRequestDTO requestBody) {
        managementClientService.cadastrarCliente(requestBody);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Conta criada com sucesso!");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
