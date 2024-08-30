package com.teste.itau.service.impl;

import com.teste.itau.dto.request.TransferRequestDTO;
import com.teste.itau.dto.response.TransferResponseDTO;
import com.teste.itau.exception.CustomApiException;
import com.teste.itau.exception.NotFoundException;
import com.teste.itau.mapper.TransferMapper;
import com.teste.itau.model.Client;
import com.teste.itau.model.Transfer;
import com.teste.itau.repository.ManagementClientRepository;
import com.teste.itau.repository.ManagementTransferRepository;
import com.teste.itau.service.ManagementTransferService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.teste.itau.mapper.TransferMapper.transferDtoToEntity;
import static com.teste.itau.mapper.TransferMapper.transferEntityToDto;

@Log4j2
@Service
public class ManagementTransferServiceImpl implements ManagementTransferService {

    @Autowired
    private ManagementClientRepository clientRepository;
    @Autowired
    private ManagementTransferRepository transferRepository;

    @Transactional
    public TransferResponseDTO realizarTransferencia(TransferRequestDTO request) {
        log.info("Request Transfer {}", request);
        Client clienteContaOrigem = clientRepository.findByNumeroConta(request.getContaOrigem())
                .orElseThrow(() -> new CustomApiException("Conta origem não encontrada!"));
        Client clienteContaDestino = clientRepository.findByNumeroConta(request.getContaDestino())
                .orElseThrow(() -> new CustomApiException("Conta destino não encontrada!"));
        Transfer transfer = transferDtoToEntity(request);
        try {
            validarTransferencia(request,clienteContaOrigem, transfer);
            log.info("Tranferência salva com sucesso!");
            return efetivarTransferencia(clienteContaOrigem, clienteContaDestino, transfer, request.getValor());
        } catch (DataAccessException e) {
            log.error("Erro ao tentar realizar transferência!", e);
            throw new CustomApiException("Erro ao tentar realizar a transferência.");
        }finally {
            salvarHistoricoDeFalha(transfer);
        }
    }

    private void validarTransferencia(TransferRequestDTO request, Client clienteContaOrigem, Transfer transfer) {
        if (clienteContaOrigem.getSaldo() < request.getValor()) {
            transfer.setSucesso(false);
            log.error("Saldo Insuficiente!");
            throw new CustomApiException("Saldo insuficiente na conta de origem.");
        }
        if (request.getValor() > 10000.00) {
            transfer.setSucesso(false);
            log.error("Valor deve ser menor que 10.000,00 reais");
            throw new CustomApiException("O valor da transferência excedeu o limite permitido de R$ 10.000,00.");
        }
    }

    private TransferResponseDTO efetivarTransferencia(Client clienteContaOrigem, Client clienteContaDestino, Transfer transfer, Double valor) {
        clienteContaOrigem.setSaldo(clienteContaOrigem.getSaldo() - valor);
        clienteContaDestino.setSaldo(clienteContaDestino.getSaldo() + valor);
        clientRepository.save(clienteContaOrigem);
        clientRepository.save(clienteContaDestino);
        transfer.setSucesso(true);
        return transferEntityToDto(transferRepository.save(transfer));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void salvarHistoricoDeFalha(Transfer transfer) {
        transferRepository.save(transfer);
    }

    @Override
    public List<TransferResponseDTO> listarTransferenciasPorConta(String numeroConta) {
        log.info("Listando transferencia pela conta número {} ", numeroConta);
        List<Transfer> historico = transferRepository.findByNumeroContaOrigemOrNumeroContaDestinoOrderByDataTransferenciaDesc(numeroConta, numeroConta);
        if(historico.isEmpty()){
            log.error("Nenhuma transferência para o número de conta {} foi encontrada!", numeroConta);
            throw new NotFoundException("Nenhuma transferência para essa conta foi encontrada!");
        }
        return historico.stream().map(TransferMapper::transferEntityToDto).collect(Collectors.toList());
    }

}
