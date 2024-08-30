package com.teste.itau.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teste.itau.dto.response.ClientResponseDTO;
import com.teste.itau.dto.response.ErrorApiResponseDTO;
import com.teste.itau.dto.request.ClientRequestDTO;
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
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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
                    schema = @Schema(implementation = ClientResponseDTO.class))
            }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorApiResponseDTO.class))
                    })
    })
    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<ClientResponseDTO> cadastrarCliente(@Valid @RequestBody ClientRequestDTO requestBody){
        ClientResponseDTO clientResponseDTO = managementClientService.cadastrarCliente(requestBody);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(clientResponseDTO);
    }

    @Operation(summary = "Lista todos os clientes", description = "Retorna todos os clientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorApiResponseDTO.class))})
    })
    @GetMapping("/listar-clientes")
    public ResponseEntity<List<ClientResponseDTO>> listarClientes() {
        return new ResponseEntity<>(managementClientService.listarClientes(), HttpStatus.OK);
    }

    @Operation(summary = "Busca cliente por número da conta", description = "Busca um cliente pelo número de conta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorApiResponseDTO.class))})
    })
    @GetMapping("buscar-cliente/{numeroConta}")
    public ResponseEntity<ClientResponseDTO> buscarClientePorNumeroConta(@PathVariable String numeroConta) {
        ClientResponseDTO cliente = managementClientService.buscarClientePorNumeroConta(numeroConta);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }
}
