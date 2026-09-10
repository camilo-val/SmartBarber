package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.barbershop;

import com.smartbarber.application.usecase.barbershop.BarbershopUpdateUC;
import com.smartbarber.application.usecase.barbershop.FindBarbershopUC;
import com.smartbarber.application.usecase.barbershop.CreateBarbershopUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barbershop.BarbershopRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.barbershop.BarbershopEntryMapper;
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
public class BarbershopHandler {
    private final BarbershopEntryMapper mapper;
    private final CreateBarbershopUC createBarbershopUC;
    private final validateRequest validateRequest;
    private final FindBarbershopUC findBarbershopUC;
    private final BarbershopUpdateUC BarbershopUpdateUC;

    public Mono<ServerResponse> createBarbershop(ServerRequest request){
        return request.bodyToMono(BarbershopRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(createBarbershopUC::crearBarberia)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)) );
    }

    public Mono<ServerResponse> findBarbershopByNae(ServerRequest request){
        return findBarbershopUC.buscarPorNombre(request.pathVariable("name"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> findBarbershopByCompanyName(ServerRequest request){
        return findBarbershopUC.buscarPorRazonSocial(request.pathVariable("companyName"))
                .map(mapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> findByBarbershopByDocument(ServerRequest request){
        return findBarbershopUC.bucarPorDocumento(request.pathVariable("document"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> findBarbershopById(ServerRequest request){
        return findBarbershopUC.buscarPorId(request.pathVariable("id"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateBarbershop(ServerRequest request){
        return request.bodyToMono(BarbershopRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(barberia -> BarbershopUpdateUC
                        .barberUpdate(request.pathVariable("id"), barberia))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.accepted().bodyValue(response));
    }
}
