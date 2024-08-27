package com.teste.itau.controller;

import com.teste.itau.dto.request.TransferRequestDTO;
import com.teste.itau.service.ManagementTransferService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> realizarTransferencia(@RequestBody TransferRequestDTO request) {
        transferService.realizarTransferencia(request);
        return new ResponseEntity<>("Transferência realizada com sucesso!", HttpStatus.OK);
    }

  /*  @GetMapping("/{numeroConta}")
    public ResponseEntity<List<Transferencia>> listarTransferencias(@PathVariable String numeroConta) {
        List<Transferencia> historico = transferService.listarTransferenciasPorConta(numeroConta);
        if (!historico.isEmpty()) {
            return new ResponseEntity<>(historico, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
}
