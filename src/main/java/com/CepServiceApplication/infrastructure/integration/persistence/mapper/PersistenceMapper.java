package com.CepServiceApplication.infrastructure.integration.persistence.mapper;


import com.CepServiceApplication.domain.entity.Address;
import com.CepServiceApplication.domain.entity.Client;
import com.CepServiceApplication.infrastructure.integration.persistence.entity.AddressEntity;
import com.CepServiceApplication.infrastructure.integration.persistence.entity.ClientEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersistenceMapper {

    Client toDomain(ClientEntity entity);

    ClientEntity toEntity(Client domain);

    Address toDomain(AddressEntity entity);

    AddressEntity toEntity(Address domain);
}