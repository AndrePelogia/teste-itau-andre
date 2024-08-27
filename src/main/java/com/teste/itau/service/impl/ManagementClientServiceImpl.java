package com.teste.itau.service.impl;

import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.exception.BDException;
import com.teste.itau.exception.ContaExistenteException;
import com.teste.itau.model.Client;
import com.teste.itau.repository.ManagementClientRepository;
import com.teste.itau.service.ManagementClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class ManagementClientServiceImpl implements ManagementClientService {

    @Autowired
    private ManagementClientRepository clientRepository;

    public void cadastrarCliente(ClientRequestDTO request) {
        log.info("Request Cliente {}", request);
        if(validarNumeroConta(request.getNumeroConta())){
            Client clientPersist = criarCliente(request);
            try {
                clientRepository.save(clientPersist);
            }catch (DataAccessException e){
                log.error("Erro ao tentar salvar",e);
                throw new BDException("Erro ao tentar salvar", e);
            }
        }else{
            log.error("Número da conta já existe!");
            throw new ContaExistenteException("Número da conta já existe!");
        }
    }

    private Client criarCliente(ClientRequestDTO request) {
        Client clientPersist = new Client();
        clientPersist.setNome(request.getNome());
        clientPersist.setNumeroConta(request.getNumeroConta());
        clientPersist.setSaldo(request.getSaldo());
        return clientPersist;
    }


    public List<ClientRequestDTO> listarClientes() {
        List<Client> clientList = clientRepository.findAll();
        List<ClientRequestDTO> listaClienteDTO = new ArrayList<>();
        clientList.forEach(client -> {
            ClientRequestDTO clientRequestDTO = new ClientRequestDTO(client.getNome(),client.getNumeroConta(),client.getSaldo());
            listaClienteDTO.add(clientRequestDTO);
        });
        return listaClienteDTO;
    }

    public ClientRequestDTO buscarClientePorNumeroConta(String numeroConta) {
        Client cliente = clientRepository.findByNumeroConta(numeroConta).orElseThrow(()-> new ContaExistenteException("Conta não encontrada!"));
        return converterParaDTO(cliente);
    }


    private boolean validarNumeroConta(String numeroConta) {
        return !clientRepository.existsByNumeroConta(numeroConta);
    }

    private ClientRequestDTO converterParaDTO(Client client) {
        ClientRequestDTO dto = new ClientRequestDTO(client.getNome(),client.getNumeroConta(),client.getSaldo());
        return dto;
    }
}
