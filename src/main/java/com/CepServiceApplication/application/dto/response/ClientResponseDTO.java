package com.CepServiceApplication.application.dto.response;

import java.time.LocalDateTime;

public record ClientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        AddressResponseDTO address,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
