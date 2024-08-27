package com.teste.itau.service;

import com.teste.itau.dto.request.ClientRequestDTO;

import java.util.List;

public interface ManagementClientService {

    void cadastrarCliente(ClientRequestDTO request);

    List<ClientRequestDTO> listarClientes();

    ClientRequestDTO buscarClientePorNumeroConta(String numeroConta);
}
