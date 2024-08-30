package com.teste.itau.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;
import com.teste.itau.exception.BDException;
import com.teste.itau.exception.CustomApiException;
import com.teste.itau.model.Client;
import com.teste.itau.repository.ManagementClientRepository;
import com.teste.itau.service.impl.ManagementClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ManagementClientServiceTest {
    @Mock
    private ManagementClientRepository clientRepository;

    @InjectMocks
    private ManagementClientServiceImpl managementClientService;

    public ManagementClientServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldSaveClientTest() throws JsonProcessingException {
        Client client = new Client();
        client.setId(new UUID(3,40));
        client.setNumeroConta("123456-7");
        client.setNome("André Silva");
        client.setSaldo(2000.0);
        when(clientRepository.save(any(Client.class))).thenReturn(client);
        ClientRequestDTO requestDTO = new ClientRequestDTO("André Silva","123456-7",2000.0);
        ClientResponseDTO clientResponseDTO = managementClientService.cadastrarCliente(requestDTO);
        verify(clientRepository, times(1)).save(any(Client.class));
        assertEquals("123456-7", clientResponseDTO.getNumeroConta());
    }

    @Test
    public void shouldThrowContaExistenteExceptionTest() {
        when(clientRepository.existsByNumeroConta(any(String.class))).thenReturn(true);
        ClientRequestDTO requestDTO = new ClientRequestDTO("André Silva","123456-7",2000.0);
        assertThrows(CustomApiException.class, () -> {
            managementClientService.cadastrarCliente(requestDTO);
        }, "Lançar exception quando Número da conta já existe!");
    }

    @Test
    public void shouldThrowDataAccessExceptionTest() {
        doThrow(new DataAccessException("Simulated database access error") {}).when(clientRepository).save(any(Client.class));
        ClientRequestDTO requestDTO = new ClientRequestDTO("André Silva","123456-7",2000.0);
        assertThrows(BDException.class, () -> {
            managementClientService.cadastrarCliente(requestDTO);
        }, "Deverá lançar DataAccessException quando ocorrer uma falha ao salvar o cliente.");
    }

}
