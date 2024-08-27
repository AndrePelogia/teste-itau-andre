package com.teste.itau.repository;

import com.teste.itau.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ManagementClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByNumeroConta(String accountNumber);

    Optional<Client> findByNumeroConta(String accountNumber);
}
