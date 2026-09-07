package com.smartbarber.application.usecase.Cliente;

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

    public Mono<Client> actualizarCliente(String id, Client client){

        return clientPort.buscarClientePorId(UUID.fromString(id))
                .map( clientDB -> Client.actualizar(clientDB.getId(), clientDB.getUserId(), client.getDocumento(),
                        client.getTipoDocumento(), client.getNombre(), client.getCelular(), client.getCorreo(),
                        clientDB.getFechaCreacion()))
                .flatMap( clienteModificado -> clientPort.actualizarCliente(UUID.fromString(id), clienteModificado));
    }
}
