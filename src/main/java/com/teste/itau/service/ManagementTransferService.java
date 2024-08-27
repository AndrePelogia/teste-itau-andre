package com.teste.itau.service;

import com.teste.itau.dto.request.TransferRequestDTO;

public interface ManagementTransferService {

    void realizarTransferencia(TransferRequestDTO request);

    // List<Transferencia> listarTransferenciasPorConta(String numeroConta);
}
