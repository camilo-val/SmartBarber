package com.smartbarber.application.usecase;

import com.smartbarber.application.port.ClientPort;
import com.smartbarber.domain.exceptions.MessageExceptionsClient;
import com.smartbarber.domain.exceptions.ClientExceptions;
import com.smartbarber.domain.model.client.Client;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
@AllArgsConstructor
public class CreateClientUC {
    private final ClientPort clientPort;

    public Mono<Client> crearCliente(Client client){
        return clientPort.existeClientePorNombre(client.getNombre())
                .flatMap( exist-> {
                    if (Boolean.TRUE.equals(exist)){
                        Mono.error(new ClientExceptions(MessageExceptionsClient.CLIENT_EXISTENTE));
                    }
                    return Mono.just(client);
                })
                .flatMap(clientPort::crearCliente);
    }
}
