package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.client;

import com.smartbarber.application.usecase.UpdateClientUC;
import com.smartbarber.application.usecase.CreateClientUC;
import com.smartbarber.application.usecase.SearchClientUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client.ClientRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.ExcepcionesTecnicas;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.MensajesExcepcionesTecnicas;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.client.ClientEntryMapper;
import com.smartbarber.infrastructure.entrypoint.utils.ValidacionRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class ClientHandler {
    private final ClientEntryMapper mapper;
    private final CreateClientUC createClientUC;
    private final ValidacionRequest validacionRequest;
    private final UpdateClientUC updateClientUC;
    private final SearchClientUC searchClientUC;

    public Mono<ServerResponse> crearCliente(ServerRequest request){
        return request.bodyToMono(ClientRqDto.class)
                .doOnNext(validacionRequest::validar)
                .map(mapper::toDomain)
                .flatMap(createClientUC::crearCliente)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new ExcepcionesTecnicas(MensajesExcepcionesTecnicas.BAD_REQUEST)));
    }

    public Mono<ServerResponse> buscarClientePorNombre(ServerRequest request){
        return searchClientUC.buscarPorNombre(request.pathVariable("nombre"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarClientePorDocuemnto(ServerRequest request){
        return searchClientUC.bucarPorDocumento(request.pathVariable("documento"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarClientePorId(ServerRequest request){
        return searchClientUC.buscarPorId(request.pathVariable("id"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> actualizarCliente(ServerRequest request){
        return request.bodyToMono(ClientRqDto.class)
                .doOnNext(validacionRequest::validar)
                .map(mapper::toDomain)
                .flatMap( client -> updateClientUC
                        .actualizarCliente(request.pathVariable("id"), client))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.accepted().bodyValue(response));
    }
}
