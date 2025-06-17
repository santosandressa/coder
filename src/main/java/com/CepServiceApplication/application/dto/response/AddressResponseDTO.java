package com.CepServiceApplication.application.dto.response;

public record AddressResponseDTO(
        String cep,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state
) {}