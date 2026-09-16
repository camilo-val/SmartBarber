package com.smartbarber.application.usecase.client;

import com.smartbarber.domain.port.ClientPort;
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
        return clientPort.existsByDocument(client.getDocumento())
                .flatMap( exist-> {
                    System.out.println("Existe cliente: +" + exist);
                    if (Boolean.TRUE.equals(exist)){
                        return Mono.error(() -> new BusinessExceptions(ClientMessageExceptions.CLIENT_EXISTENTE));
                    }
                    return Mono.just(client);
                })
                .flatMap(clientPort::crearCliente);
    }
}
