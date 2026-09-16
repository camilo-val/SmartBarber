package com.smartbarber.domain.port;

import com.smartbarber.domain.model.client.Client;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ClientPort {
    Mono<Client> buscarClientePorId(UUID id);
    Mono<Client> buscarClientePorNombre(String nombre);
    Mono<Client> buscarPorDocuemnto(String documento);
    Mono<Client> crearCliente(Client client);
    Mono<Client> actualizarCliente(UUID id, Client client);
    Mono<Void> eliminarCliente(UUID id);
    Mono<Boolean> existsByDocument(String document);
}
