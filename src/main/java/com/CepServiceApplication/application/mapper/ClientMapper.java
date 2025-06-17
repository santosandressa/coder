package com.CepServiceApplication.application.mapper;

import com.CepServiceApplication.application.dto.request.ClientRequestDTO;
import com.CepServiceApplication.application.dto.response.AddressResponseDTO;
import com.CepServiceApplication.application.dto.response.ClientResponseDTO;
import com.CepServiceApplication.domain.entity.Address;
import com.CepServiceApplication.domain.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "address", ignore = true)
    Client toEntity(ClientRequestDTO dto);

    ClientResponseDTO toResponseDto(Client client);

    AddressResponseDTO addressToDto(Address address);
}
