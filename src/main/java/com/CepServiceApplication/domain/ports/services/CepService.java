package com.CepServiceApplication.domain.ports.services;

import com.CepServiceApplication.domain.entity.Address;

public interface CepService {
    Address getAddressByCep(String cep);
}
