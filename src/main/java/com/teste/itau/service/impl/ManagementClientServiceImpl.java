package com.teste.itau.service.impl;

import com.teste.itau.dto.request.ClientRequestDTO;
import com.teste.itau.dto.response.ClientResponseDTO;
import com.teste.itau.exception.BDException;
import com.teste.itau.exception.CustomApiException;
import com.teste.itau.exception.NotFoundException;
import com.teste.itau.mapper.ClientMapper;
import com.teste.itau.model.Client;
import com.teste.itau.repository.ManagementClientRepository;
import com.teste.itau.service.ManagementClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.teste.itau.mapper.ClientMapper.clientRequestToEntity;
import static com.teste.itau.mapper.ClientMapper.clientToClientResponseDTO;

@Log4j2
@Service
public class ManagementClientServiceImpl implements ManagementClientService {

    @Autowired
    private ManagementClientRepository clientRepository;

    public ClientResponseDTO cadastrarCliente(ClientRequestDTO request)  {
        log.info("Request Cliente {}", request);
        if(validarNumeroConta(request.getNumeroConta())){
            try {
                Client response = clientRepository.save(clientRequestToEntity(request));
                return clientToClientResponseDTO(response);
            }catch (DataAccessException e){
                log.error("Erro ao tentar salvar",e);
                throw new BDException("Erro ao tentar salvar", e);
            }
        }else{
            log.error("Número da conta já existe!");
            throw new CustomApiException("Número da conta já existe!");
        }
    }

    public List<ClientResponseDTO> listarClientes() {
        try{
            List<Client> listaCliente = clientRepository.findAll();
            if(listaCliente.isEmpty()){
                log.info("Nenhum cliente encontrado!");
                throw new NotFoundException("Nenhum cliente encontrado!");
            }
            return listaCliente.stream()
                    .map(ClientMapper::clientToClientResponseDTO)
                    .collect(Collectors.toList());
        }catch (DataAccessException e){
            log.error("Erro ao tentar buscar clientes",e);
            throw new BDException("Erro ao tentar buscar clientes",e);
        }
    }

    public ClientResponseDTO buscarClientePorNumeroConta(String numeroConta) {
        log.info("Número da conta {}", numeroConta);
        Client cliente = clientRepository.findByNumeroConta(numeroConta).orElseThrow(()-> {
            log.info("Conta não encontrada {}", numeroConta);
            return new NotFoundException("Conta não encontrada!");
        });
        return clientToClientResponseDTO(cliente);
    }

    private boolean validarNumeroConta(String numeroConta) {
        return !clientRepository.existsByNumeroConta(numeroConta);
    }

}
