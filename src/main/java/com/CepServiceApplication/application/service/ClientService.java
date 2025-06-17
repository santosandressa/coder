package com.CepServiceApplication.application.service;

import com.CepServiceApplication.application.dto.request.ClientRequestDTO;
import com.CepServiceApplication.application.dto.response.ClientResponseDTO;
import com.CepServiceApplication.application.mapper.ClientMapper;
import com.CepServiceApplication.domain.entity.Address;
import com.CepServiceApplication.domain.entity.Client;
import com.CepServiceApplication.domain.ports.repository.IClientRepository;
import com.CepServiceApplication.domain.ports.services.CepService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {

    // S - Single Responsibility Principle: Esta classe tem a responsabilidade única de gerenciar operações relacionadas a clientes
    // O - Open/Closed Principle: A classe está aberta para extensão (podemos adicionar novos métodos) mas fechada para modificação
    // D - Dependency Inversion Principle: Depende de abstrações (IClientRepository, CepService) e não de implementações concretas
    
    private static final Logger log = LoggerFactory.getLogger(ClientService.class);
    private final IClientRepository clientRepository;
    private final CepService cepService;
    private final ClientMapper clientMapper;

    public List<ClientResponseDTO> findAllClients() {
        log.info("Buscando todos os clientes");
        return clientRepository.findAll().stream()
                .map(clientMapper::toResponseDto)
                .toList();
    }

    public ClientResponseDTO findClientById(Long id) {
        log.info("Buscando cliente com ID: {}", id);
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        return clientMapper.toResponseDto(client);
    }

    public ClientResponseDTO createClient(ClientRequestDTO requestDTO) {
        log.info("Criando novo cliente: {}", requestDTO.name());

        validateCpfNotExists(requestDTO.cpf());

        var address = buildClientAddress(requestDTO);
        var client = buildClientWithAddress(requestDTO, address);

        return saveClientAndCreateResponse(client);
    }

    public ClientResponseDTO updateClient(Long id, ClientRequestDTO requestDTO) {
        log.info("Atualizando cliente com ID: {}", id);
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));

        Address address = cepService.getAddressByCep(requestDTO.cep());
        address.setNumber(requestDTO.number());
        address.setComplement(requestDTO.complement());

        existingClient.setName(requestDTO.name());
        existingClient.setEmail(requestDTO.email());
        existingClient.setAddress(address);

        Client updatedClient = clientRepository.save(existingClient);
        return clientMapper.toResponseDto(updatedClient);
    }

    public void deleteClient(Long id) {
        log.info("Removendo cliente com ID: {}", id);

        clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));

        clientRepository.delete(id);
    }

    private void validateCpfNotExists(String cpf) {
        clientRepository.findByCpf(cpf)
                .ifPresent(existingClient -> {
                    throw new RuntimeException("CPF já cadastrado");
                });
    }

    private Address buildClientAddress(ClientRequestDTO requestDTO) {
        Address address = cepService.getAddressByCep(requestDTO.cep());
        address.setNumber(requestDTO.number());
        address.setComplement(requestDTO.complement());
        return address;
    }

    private Client buildClientWithAddress(ClientRequestDTO requestDTO, Address address) {
        Client client = clientMapper.toEntity(requestDTO);
        client.setAddress(address);
        return client;
    }

    private ClientResponseDTO saveClientAndCreateResponse(Client client) {
        Client savedClient = clientRepository.save(client);
        return clientMapper.toResponseDto(savedClient);
    }
}