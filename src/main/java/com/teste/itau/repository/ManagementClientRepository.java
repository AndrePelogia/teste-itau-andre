package com.teste.itau.repository;

import com.teste.itau.model.Client;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ManagementClientRepository extends JpaRepository<Client, UUID> {
    boolean existsByNumeroConta(String accountNumber);
    Optional<Client> findByNumeroConta(String accountNumber);
}
