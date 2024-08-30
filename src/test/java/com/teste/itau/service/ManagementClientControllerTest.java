package com.teste.itau.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teste.itau.controller.ManagementClientController;
import com.teste.itau.dto.request.ClientRequestDTO;
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

import static com.teste.itau.service.MotherTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        when(service.cadastrarCliente(any(ClientRequestDTO.class))).thenReturn(getClientResponseSingle());
        mockMvc.perform(post("/api/teste/cadastrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(getClientRequestSingle())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("André Silva"))
                .andExpect(jsonPath("$.numeroConta").value("123456-7"))
                .andExpect(jsonPath("$.saldo").value(2000.0));
    }

    @Test
    public void testCadastrarClienteBadRequest() throws Exception {
        String invalidRequest = "{\"nome\":\"\",\"numeroConta\":\"123456-7\",\"saldo\":2000.0}";
        mockMvc.perform(post("/api/teste/cadastrar-cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void shouldListarClientes() throws Exception {
        when(service.listarClientes()).thenReturn(getClientResponseList());
        mockMvc.perform(get("/api/teste/listar-clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$.[0].nome").value(getClientResponseList().get(0).getNome()))
                .andExpect(jsonPath("$.[1].nome").value(getClientResponseList().get(1).getNome()))
                .andDo(print());
        verify(service, times(1)).listarClientes();
    }
}
