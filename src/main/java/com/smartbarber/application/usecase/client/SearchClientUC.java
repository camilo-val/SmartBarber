package com.smartbarber.application.usecase.client;

import com.smartbarber.domain.port.ClientPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.client.ClientMessageExceptions;
import com.smartbarber.domain.model.client.Client;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class SearchClientUC {
    private final ClientPort clientPort;

    public Mono<Client> buscarPorNombre(String nombre){
        return clientPort.buscarClientePorNombre(nombre)
                .doOnNext(client -> log.info("Datos encontrados {}", client))
                .switchIfEmpty(Mono.error(() -> (new BusinessExceptions(ClientMessageExceptions.CLIENT_NO_EXISTE))));
    }

    public Mono<Client> bucarPorDocumento(String documento) {
        return clientPort.buscarPorDocuemnto(documento)
                .doOnNext(client -> log.info("Datos encontrados {}", client))
                .switchIfEmpty(Mono.error(() -> (new BusinessExceptions(ClientMessageExceptions.CLIENT_NO_EXISTE))));
    }

    public Mono<Client> buscarPorId(String id){
        return clientPort.buscarClientePorId(UUID.fromString(id))
                .doOnNext(client -> log.info("Datos encontrados {}", client))
                .switchIfEmpty(Mono.error(() -> (new BusinessExceptions(ClientMessageExceptions.CLIENT_NO_EXISTE))));
    }
}
