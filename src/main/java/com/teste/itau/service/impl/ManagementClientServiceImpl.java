package com.teste.itau.service.impl;

import com.teste.itau.dto.ManagementClientRequestDTO;
import com.teste.itau.exception.BDException;
import com.teste.itau.exception.ContaExistenteException;
import com.teste.itau.model.Client;
import com.teste.itau.repository.ManagementClientRepository;
import com.teste.itau.service.ManagementClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class ManagementClientServiceImpl implements ManagementClientService {

    @Autowired
    private ManagementClientRepository clientRepository;
    @Override
    public void cadastrarCliente(ManagementClientRequestDTO request) {
        log.info("Request Cliente {}", request);
        if(validarNumeroConta(request.getNumeroConta())){
            Client clientPersist = new Client();
            clientPersist.setNome(request.getNome());
            clientPersist.setNumeroConta(request.getNumeroConta());
            clientPersist.setSaldo(request.getSaldo());
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

    private boolean validarNumeroConta(String numeroConta) {
        return !clientRepository.existsByNumeroConta(numeroConta);
    }
}
