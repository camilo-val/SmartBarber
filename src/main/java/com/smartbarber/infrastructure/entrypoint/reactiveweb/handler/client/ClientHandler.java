package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.client;

import com.smartbarber.application.usecase.client.UpdateClientUC;
import com.smartbarber.application.usecase.client.CreateClientUC;
import com.smartbarber.application.usecase.client.SearchClientUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.client.ClientRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.client.ClientEntryMapper;
import com.smartbarber.infrastructure.entrypoint.utils.validateRequest;
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
    private final validateRequest validateRequest;
    private final UpdateClientUC updateClientUC;
    private final SearchClientUC searchClientUC;

    public Mono<ServerResponse> crearCliente(ServerRequest request){
        return request.bodyToMono(ClientRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(createClientUC::crearCliente)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> buscarClientePorNombre(ServerRequest request){
        return searchClientUC.buscarPorNombre(request.pathVariable("name"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarClientePorDocuemnto(ServerRequest request){
        return searchClientUC.bucarPorDocumento(request.pathVariable("document"))
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
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap( client -> updateClientUC
                        .actualizarCliente(request.pathVariable("id"), client))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.accepted().bodyValue(response));
    }
}
