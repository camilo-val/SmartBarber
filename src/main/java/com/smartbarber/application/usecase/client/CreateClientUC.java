package com.smartbarber.application.usecase.client;

import com.smartbarber.application.port.ClientPort;
import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.client.ClientMessageExceptions;
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
                        Mono.error(new BusinessExceptions(ClientMessageExceptions.CLIENT_EXISTENTE));
                    }
                    return Mono.just(client);
                })
                .flatMap(clientPort::crearCliente);
    }
}
