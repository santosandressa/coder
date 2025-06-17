package com.CepServiceApplication.infrastructure.integration.persistence.repository;

import com.CepServiceApplication.infrastructure.integration.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByCpf(String cpf);
}