package com.smartbarber.domain.port;

import com.smartbarber.domain.model.client.Client;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientPort {
    Mono<Client> findById(UUID id);
    Mono<Client> findByName(String name);
    Mono<Client> findByDocument(String document);
    Mono<Client> save(Client client);
    Mono<Client> update(UUID id, Client client);
    Mono<Void> eliminarCliente(UUID id);
    Mono<Boolean> existsByDocument(String document);
}
