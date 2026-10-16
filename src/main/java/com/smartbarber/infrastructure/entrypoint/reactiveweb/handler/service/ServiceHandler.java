package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.service;

import com.smartbarber.application.usecase.service.CreateServiceUC;
import com.smartbarber.application.usecase.service.SearchServiceUC;
import com.smartbarber.application.usecase.service.UpdateServiceUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.service.ServiceRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.utils.validateRequest;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.service.ServiceEntryMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class ServiceHandler {

    private final ServiceEntryMapper mapper;
    private final CreateServiceUC createServiceUC;
    private final validateRequest validateRequest;
    private final UpdateServiceUC updateServiceUC;
    private final SearchServiceUC searchServiceUC;

    public Mono<ServerResponse> createService(ServerRequest request){
        return request.bodyToMono(ServiceRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toCommand)
                .flatMap(createServiceUC::createService)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }


    public Mono<ServerResponse> findServiceByName(ServerRequest request) {
        return searchServiceUC.findByName(request.pathVariable("name"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> findServiceById(ServerRequest request) {
        return Mono.fromCallable(() ->
                        Integer.valueOf(request.pathVariable("id")))
                .flatMap(searchServiceUC::findById)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> updateService(ServerRequest request) {
        return Mono.fromCallable(() ->
                        Integer.valueOf(request.pathVariable("id")))
                .flatMap(id -> request.bodyToMono(ServiceRqDto.class)
                        .doOnNext(validateRequest::validate)
                        .map(mapper::toCommand)
                        .flatMap(service -> updateServiceUC.updateService(id, service)))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.accepted().bodyValue(response));
    }

    public Mono<ServerResponse> findServicesByBarberiaId(
            ServerRequest request
    ) {
        return Mono.fromCallable(() ->
                        java.util.UUID.fromString(
                                request.pathVariable("barberiaId")
                        ))
                .flatMapMany(searchServiceUC::findByBarberiaId)
                .map(mapper::toResponse)
                .collectList()
                .flatMap(services ->
                        ServerResponse.ok().bodyValue(services)
                );
    }

}
