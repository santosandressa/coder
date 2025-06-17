package com.CepServiceApplication.infrastructure.integration.persistence.repository.impl;

import com.CepServiceApplication.domain.entity.Client;
import com.CepServiceApplication.domain.ports.repository.IClientRepository;
import com.CepServiceApplication.infrastructure.integration.persistence.entity.ClientEntity;
import com.CepServiceApplication.infrastructure.integration.persistence.mapper.PersistenceMapper;
import com.CepServiceApplication.infrastructure.integration.persistence.repository.ClientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ClientRepositoryImpl implements IClientRepository {

    // S - Single Responsibility Principle: Responsável apenas pela persistência de clientes
    // L - Liskov Substitution Principle: Implementa corretamente a interface IClientRepository
    // O - Open/Closed Principle: Aberta para extensão, fechada para modificação

    private final ClientJpaRepository clientJpaRepository;
    private final PersistenceMapper persistenceMapper;

    @Override
    public List<Client> findAll() {
        return clientJpaRepository.findAll().stream()
                .map(persistenceMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Client> findById(Long id) {
        return clientJpaRepository.findById(id)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Optional<Client> findByCpf(String cpf) {
        return clientJpaRepository.findByCpf(cpf)
                .map(persistenceMapper::toDomain);
    }

    @Override
    public Client save(Client client) {
        ClientEntity entity = persistenceMapper.toEntity(client);
        ClientEntity savedEntity = clientJpaRepository.save(entity);
        return persistenceMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(Long id) {
        clientJpaRepository.deleteById(id);
    }
}