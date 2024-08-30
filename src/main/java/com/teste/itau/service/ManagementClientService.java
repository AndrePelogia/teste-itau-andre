package com.teste.itau.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;

import java.util.List;

public interface ManagementClientService {

    ClientResponseDTO cadastrarCliente(ClientRequestDTO request) ;

    List<ClientResponseDTO> listarClientes();

    ClientResponseDTO buscarClientePorNumeroConta(String numeroConta);
}
