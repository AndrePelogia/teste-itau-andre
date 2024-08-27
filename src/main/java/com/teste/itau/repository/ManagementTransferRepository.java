package com.teste.itau.repository;

import com.teste.itau.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ManagementTransferRepository extends JpaRepository<Transfer, UUID> {
}
