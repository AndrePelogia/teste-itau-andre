package com.teste.itau.controller;

import com.teste.itau.dto.request.TransferRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;
import com.teste.itau.dto.response.ErrorApiResponseDTO;
import com.teste.itau.dto.response.TransferResponseDTO;
import com.teste.itau.service.ManagementTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teste")
public class ManagementTransferController
{
    @Autowired
    private ManagementTransferService transferService;

    @Operation(summary = "Realizar transferência entre contas",
            description = "Realiza a transferência de um valor entre duas contas, verificando se há saldo suficiente e respeitando o limite de R$ 10.000,00.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transferência realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor ao processar a transferência")
    })
    @PostMapping("/realizar-transferencia")
    public ResponseEntity<TransferResponseDTO> realizarTransferencia(@RequestBody TransferRequestDTO request) {
        TransferResponseDTO transferResponseDTO = transferService.realizarTransferencia(request);
        return new ResponseEntity<>(transferResponseDTO, HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as transações de uma conta", description = "Retorna todos os registros de transferência de um número de conta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transações listadas com sucesso", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ClientResponseDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Nenhuma transação para o número de conta encontrada", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorApiResponseDTO.class))})
    })
    @GetMapping("/tranferencia-historico/{numeroConta}")
    public ResponseEntity<List<TransferResponseDTO>> listarTransferencias(@PathVariable String numeroConta) {
        List<TransferResponseDTO> historico = transferService.listarTransferenciasPorConta(numeroConta);
        return new ResponseEntity<>(historico, HttpStatus.OK);
    }
}
