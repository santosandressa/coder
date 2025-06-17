package com.CepServiceApplication.domain.ports.repository;

import com.CepServiceApplication.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientRepository {
    List<Client> findAll();

    Optional<Client> findById(Long id);

    Optional<Client> findByCpf(String cpf);

    Client save(Client client);

    void delete(Long id);
}