package com.teste.itau.mapper;

import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;
import com.teste.itau.model.Client;


public class ClientMapper {
    public static ClientResponseDTO clientToClientResponseDTO(Client client){
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO();
        clientResponseDTO.setId(client.getId());
        clientResponseDTO.setNome(client.getNome());
        clientResponseDTO.setNumeroConta(client.getNumeroConta());
        clientResponseDTO.setSaldo(client.getSaldo());
        return clientResponseDTO;
    }

    public static Client clientRequestToEntity(ClientRequestDTO request) {
        Client clientPersist = new Client();
        clientPersist.setNome(request.getNome());
        clientPersist.setNumeroConta(request.getNumeroConta());
        clientPersist.setSaldo(request.getSaldo());
        return clientPersist;
    }
}
