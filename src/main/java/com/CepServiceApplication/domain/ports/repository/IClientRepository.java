package com.CepServiceApplication.domain.ports.repository;

import com.CepServiceApplication.domain.entity.Client;

import java.util.List;
import java.util.Optional;

public interface IClientRepository {

    // I - Interface Segregation Principle: Interface coesa com métodos relacionados apenas à persistência de clientes
    // D - Dependency Inversion Principle: Define uma abstração que será implementada por classes concretas

    List<Client> findAll();

    Optional<Client> findById(Long id);

    Optional<Client> findByCpf(String cpf);

    Client save(Client client);

    void delete(Long id);
}