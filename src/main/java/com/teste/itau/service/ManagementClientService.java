package com.teste.itau.service;

import com.teste.itau.dto.ManagementClientRequestDTO;

import java.util.List;

public interface ManagementClientService {

    void cadastrarCliente(ManagementClientRequestDTO request);

    List<ManagementClientRequestDTO> listarClientes();
}
