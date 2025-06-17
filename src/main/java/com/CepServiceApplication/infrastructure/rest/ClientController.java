package com.CepServiceApplication.infrastructure.rest;

import com.CepServiceApplication.application.dto.request.ClientRequestDTO;
import com.CepServiceApplication.application.dto.response.ClientResponseDTO;
import com.CepServiceApplication.application.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<ClientResponseDTO>>> getAllClients() {
        log.info("REST request para listar todos os clientes");

        List<EntityModel<ClientResponseDTO>> clients = clientService.findAllClients().stream()
                .map(this::toClientEntityModelWithAllLinks)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(clients,
                        linkTo(methodOn(ClientController.class).getAllClients()).withSelfRel())
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<ClientResponseDTO>> getClientById(@PathVariable Long id) {
        log.info("REST request para buscar cliente com ID: {}", id);

        ClientResponseDTO client = clientService.findClientById(id);
        return ResponseEntity.ok(toClientEntityModelWithAllLinks(client));
    }

    @PostMapping
    public ResponseEntity<EntityModel<ClientResponseDTO>> createClient(
            @Valid @RequestBody ClientRequestDTO clientRequest) {
        log.info("REST request para criar novo cliente: {}", clientRequest.name());

        ClientResponseDTO createdClient = clientService.createClient(clientRequest);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(toClientEntityModelWithAllLinks(createdClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ClientResponseDTO>> updateClient(
            @PathVariable Long id,
            @Valid @RequestBody ClientRequestDTO clientRequest) {
        log.info("REST request para atualizar cliente com ID: {}", id);

        ClientResponseDTO updatedClient = clientService.updateClient(id, clientRequest);

        return ResponseEntity.ok(toClientEntityModelWithAllLinks(updatedClient));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        log.info("REST request para deletar cliente com ID: {}", id);

        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ClientResponseDTO> toClientEntityModelWithAllLinks(ClientResponseDTO client) {
        Long clientId = client.id();
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(ClientController.class).getClientById(clientId)).withSelfRel());
        links.add(linkTo(methodOn(ClientController.class).getAllClients()).withRel("clients"));
        links.add(linkTo(methodOn(ClientController.class).deleteClient(clientId)).withRel("delete"));

        String updateUri = linkTo(ClientController.class).slash(clientId).toUri().toString();
        links.add(Link.of(updateUri).withRel("update"));

        return EntityModel.of(client, links);
    }
}