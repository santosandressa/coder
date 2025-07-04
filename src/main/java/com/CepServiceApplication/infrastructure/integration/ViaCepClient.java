package com.CepServiceApplication.infrastructure.integration;

import com.CepServiceApplication.infrastructure.integration.model.response.ViaCepResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viacep", url = "https://viacep.com.br/ws")
public interface ViaCepClient {

    @GetMapping("/{cep}/json")
    ViaCepResponse getAddressByCep(@PathVariable("cep") String cep);
}
