package com.teste.itau.service.impl;

import com.teste.itau.dto.request.TransferRequestDTO;
import com.teste.itau.exception.ContaExistenteException;
import com.teste.itau.model.Client;
import com.teste.itau.model.Transfer;
import com.teste.itau.repository.ManagementClientRepository;
import com.teste.itau.repository.ManagementTransferRepository;
import com.teste.itau.service.ManagementTransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Service
public class ManagementTransferServiceImpl implements ManagementTransferService {

    @Autowired
    private ManagementClientRepository clientRepository;
    @Autowired
    private ManagementTransferRepository transferRepository;

    @Transactional
    public void realizarTransferencia(TransferRequestDTO request) {
        Client clienteContaOrigem = clientRepository.findByNumeroConta(request.getContaOrigem())
                .orElseThrow(() -> new ContaExistenteException("Conta origem não encontrada!"));
        Client clienteContaDestino = clientRepository.findByNumeroConta(request.getContaDestino())
                .orElseThrow(() -> new ContaExistenteException("Conta destino não encontrada!"));
        Transfer transfer = transferDtoToEntity(request);
        try {
            if (clienteContaOrigem.getSaldo() < request.getValor()) {

                transfer.setSucesso(false);
                transferRepository.save(transfer);
                throw new ContaExistenteException("Saldo insuficiente na conta de origem.");
            }
            else if (request.getValor() > 10000.00) {
                transfer.setSucesso(false);
                transferRepository.save(transfer);
                throw new ContaExistenteException("O valor da transferência excedeu o limite permitido de R$ 10.000,00.");
            }else {
                clienteContaOrigem.setSaldo(clienteContaOrigem.getSaldo() - request.getValor());
                clienteContaDestino.setSaldo(clienteContaDestino.getSaldo() + request.getValor());
                transfer.setSucesso(true);
                clientRepository.save(clienteContaOrigem);
                clientRepository.save(clienteContaDestino);
            }
        }catch (DataAccessException e){
            throw new ContaExistenteException("Erro ao processar a transferência.");
        }finally {
            transferRepository.save(transfer);
        }
    }


    private Transfer transferDtoToEntity(TransferRequestDTO request) {
        Transfer transfer = new Transfer();
        transfer.setNumeroContaOrigem(request.getContaOrigem());
        transfer.setNumeroContaDestino(request.getContaDestino());
        transfer.setValor(request.getValor());
        transfer.setDataTransferencia(LocalDateTime.now());
        return transfer;
    }

   /* @Override
    public List<Transferencia> listarTransferenciasPorConta(String numeroConta) {
        return null;
    }*/
}
