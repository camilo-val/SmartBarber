package com.smartbarber.application.usecase.client;

import com.smartbarber.application.port.ClientPort;
import com.smartbarber.domain.model.client.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateClientUC {
    private final ClientPort clientPort;

    public Mono<Client> clientUpdate(String id, Client client){

        return clientPort.findById(UUID.fromString(id))
                .map( clientDB -> Client.update(clientDB.getId(), clientDB.getUserId(), client.getDocument(),
                        client.getDocumentType(), client.getName(), client.getCell(), client.getEmail(),
                        clientDB.getCreateAt()))
                .flatMap( clientUpdate -> clientPort.update(UUID.fromString(id), clientUpdate));
    }
}
