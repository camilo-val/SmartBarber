package com.smartbarber.infrastructure.drivenadapter.postgres.adapter;

import com.smartbarber.application.port.ClientPort;
import com.smartbarber.domain.model.client.Client;
import com.smartbarber.infrastructure.drivenadapter.postgres.data.ClientData;
import com.smartbarber.infrastructure.drivenadapter.postgres.mapper.ClientAdapterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class ClientAdapter implements ClientPort{
    private final ClientData clientData;
    private final ClientAdapterMapper mapper;
    @Override
    public Mono<Client> findById(UUID id){
        return clientData.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> findByName(String name){
        return clientData.findByName(name)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> findByDocument(String document){
        return clientData.findByDocument(document)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> save(Client client){
        return clientData.save(mapper.toEntity(client))
                .doOnNext( e -> log.info("Data registrada {}", e.toString()))
                .doOnSuccess( entityGuardada ->
                        log.info("Proceso de guardado finalizado correctamente"))
                .doOnError( error ->
                        log.error("Error guardando barbería", error))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Client> update(UUID id, Client client){
        return clientData.save(mapper.toEntity(client))
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> eliminarCliente(UUID id) { return clientData.deleteById(id);}

    @Override
    public Mono<Boolean> existsByDocument(String document) {
        return clientData.existsByDocument(document);
    }
}
