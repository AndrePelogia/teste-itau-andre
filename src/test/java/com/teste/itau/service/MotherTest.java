package com.teste.itau.service;

import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MotherTest {
    public static ClientRequestDTO getClientRequestSingle(){
        return new ClientRequestDTO("André Silva", "123456-7", 2000.0);
    }

    public static ClientResponseDTO getClientResponseSingle(){
        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(new UUID(12,3));
        responseDTO.setNome("André Silva");
        responseDTO.setNumeroConta("123456-7");
        responseDTO.setSaldo(2000.0);
        return responseDTO;
    }

    public static List<ClientResponseDTO> getClientResponseList(){
        List<ClientResponseDTO> listClientResponse = new ArrayList<>();
        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(new UUID(12,3));
        responseDTO.setNome("André Silva");
        responseDTO.setNumeroConta("123456-7");
        responseDTO.setSaldo(2000.0);
        listClientResponse.add(responseDTO);

        ClientResponseDTO responseDTOTwo = new ClientResponseDTO();
        responseDTO.setId(new UUID(12,3));
        responseDTO.setNome("Alice Guimarães");
        responseDTO.setNumeroConta("123456-8");
        responseDTO.setSaldo(3000.0);
        listClientResponse.add(responseDTOTwo);
        return listClientResponse;
    }
}
