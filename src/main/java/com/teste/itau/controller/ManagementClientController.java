package com.teste.itau.controller;

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
                    schema = @Schema(implementation = ClientRequestDTO.class))
            }),
            @ApiResponse(
                    responseCode = "400",
                    description = "Requisição inválida",
                    content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorApiResponseDTO.class))
                    })
    })
    @PostMapping("/cadastrar-cliente")
    public ResponseEntity<Map<String, String>> cadastrarCliente(@Valid @RequestBody ClientRequestDTO requestBody) {
        managementClientService.cadastrarCliente(requestBody);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Conta criada com sucesso!");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "Lista todos os clientes", description = "Retorna todos os clientes cadastrados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Clientes listados com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientRequestDTO.class))})
    })
    @GetMapping("/listar-clientes")
    public ResponseEntity<List<ClientRequestDTO>> listarClientes() {
        List<ClientRequestDTO> listaDeClientes = managementClientService.listarClientes();
        return new ResponseEntity<>(listaDeClientes, HttpStatus.OK);
    }

    @Operation(summary = "Busca cliente por número da conta", description = "Busca um cliente pelo número de conta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientRequestDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content)
    })
    @GetMapping("buscar-cliente/{numeroConta}")
    public ResponseEntity<ClientRequestDTO> buscarClientePorNumeroConta(@PathVariable String numeroConta) {
        ClientRequestDTO cliente = managementClientService.buscarClientePorNumeroConta(numeroConta);
        if (cliente != null) {
            return new ResponseEntity<>(cliente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
