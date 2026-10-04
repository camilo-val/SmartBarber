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
                .map(mapper::toDomain)
                .flatMap(createServiceUC::crearServicio)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> findServiceByName(ServerRequest request){
        return searchServiceUC.buscarPorNombre(request.pathVariable("name"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findServiceById(ServerRequest request){
        return searchServiceUC.buscarPorId(Integer.valueOf(request.pathVariable("id")))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateService(ServerRequest request){
        return request.bodyToMono(ServiceRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(service -> updateServiceUC
                        .serviceUpdate(request.pathVariable("id"), service))
                        .map(mapper::toResponse)
                        .flatMap( response -> ServerResponse.accepted().bodyValue(response));
    }
}
