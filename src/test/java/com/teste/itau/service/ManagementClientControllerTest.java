package com.teste.itau.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.itau.controller.ManagementClientController;
import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;
import com.teste.itau.service.impl.ManagementClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ManagementClientControllerTest {
    @InjectMocks
    private ManagementClientController controller;
    @Mock
    private ManagementClientServiceImpl service;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void shouldCadastrarCliente() throws Exception {
        ClientRequestDTO requestDTO = new ClientRequestDTO("André Silva", "123456-7", 2000.0);
        ClientResponseDTO responseDTO = new ClientResponseDTO();
        responseDTO.setId(new UUID(12,3));
        responseDTO.setNome("André Silva");
        responseDTO.setNumeroConta("123456-7");
        responseDTO.setSaldo(2000.0);

        when(service.cadastrarCliente(any(ClientRequestDTO.class))).thenReturn(responseDTO);
        mockMvc.perform(post("/api/teste/cadastrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("André Silva"))
                .andExpect(jsonPath("$.numeroConta").value("123456-7"))
                .andExpect(jsonPath("$.saldo").value(2000.0));
    }
}
