package com.CepServiceApplication.domain.ports.services;

import com.CepServiceApplication.domain.entity.Address;

public interface CepService {
    // I - Interface Segregation Principle: Interface com um único método, focada em uma responsabilidade específica
    // D - Dependency Inversion Principle: Define uma abstração para o serviço de CEP
    Address getAddressByCep(String cep);
}
