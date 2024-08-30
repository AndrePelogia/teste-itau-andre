package com.teste.itau.service;

import com.teste.itau.dto.request.TransferRequestDTO;
import com.teste.itau.dto.response.TransferResponseDTO;

import java.util.List;

public interface ManagementTransferService {

    void realizarTransferencia(TransferRequestDTO request);

    List<TransferResponseDTO> listarTransferenciasPorConta(String numeroConta);

}
