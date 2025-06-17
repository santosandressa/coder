package com.CepServiceApplication.infrastructure.integration.service;

import com.CepServiceApplication.domain.entity.Address;
import com.CepServiceApplication.domain.ports.services.CepService;
import com.CepServiceApplication.infrastructure.integration.ViaCepClient;
import com.CepServiceApplication.infrastructure.integration.model.response.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ViaCepService implements CepService {

    private static final Logger log = LoggerFactory.getLogger(ViaCepService.class);
    private final ViaCepClient viaCepClient;

    @Override
    public Address getAddressByCep(String cep) {
        log.info("Buscando endereço pelo CEP: {}", cep);

        try {
            ViaCepResponse response = viaCepClient.getAddressByCep(cep);
            return Address.builder()
                    .cep(response.getCep())
                    .street(response.getLogradouro())
                    .complement(response.getComplemento())
                    .neighborhood(response.getBairro())
                    .city(response.getLocalidade())
                    .state(response.getUf())
                    .build();
        } catch (Exception e) {
            log.error("Erro ao buscar CEP: {}", e.getMessage());
            throw new RuntimeException("Erro ao buscar CEP: " + cep, e);
        }
    }
}