package com.teste.itau.mapper;

import com.teste.itau.dto.request.TransferRequestDTO;
import com.teste.itau.dto.response.TransferResponseDTO;
import com.teste.itau.model.Transfer;

import java.time.LocalDateTime;

public class TransferMapper {

    public static Transfer transferDtoToEntity(TransferRequestDTO request) {
        Transfer transfer = new Transfer();
        transfer.setNumeroContaOrigem(request.getContaOrigem());
        transfer.setNumeroContaDestino(request.getContaDestino());
        transfer.setValor(request.getValor());
        transfer.setDataTransferencia(LocalDateTime.now());
        return transfer;
    }

    public static TransferResponseDTO transferEntityToDto(Transfer transfer){
        TransferResponseDTO response = new TransferResponseDTO();
        response.setNumeroContaOrigem(transfer.getNumeroContaOrigem());
        response.setNumeroContaDestino(transfer.getNumeroContaDestino());
        response.setValor(transfer.getValor());
        response.setSucesso(transfer.getSucesso() ? "Transferência realizada!" : "Transferência não realizada!");
        response.setDataTransferencia(transfer.getDataTransferencia());
        return response;
    }
}
