package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.barberia;

import com.smartbarber.application.usecase.ActualizarBarberiaUC;
import com.smartbarber.application.usecase.BuscarBarberiaUC;
import com.smartbarber.application.usecase.CrearBarberiaUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.barberia.BarberiaRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.ExcepcionesTecnicas;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.MensajesExcepcionesTecnicas;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.barberia.BarberiaEntryMapper;
import com.smartbarber.infrastructure.entrypoint.utils.ValidacionRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@AllArgsConstructor
@Slf4j
public class BarberiaHandler {
    private final BarberiaEntryMapper mapper;
    private final CrearBarberiaUC crearBarberiaUC;
    private final ValidacionRequest validacionRequest;
    private final BuscarBarberiaUC buscarBarberiaUC;
    private final ActualizarBarberiaUC actualizarBarberiaUC;

    public Mono<ServerResponse> crearBarberia(ServerRequest request){
        return request.bodyToMono(BarberiaRqDto.class)
                .doOnNext(validacionRequest::validar)
                .map(mapper::toDomain)
                .flatMap(crearBarberiaUC::crearBarberia)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new ExcepcionesTecnicas(MensajesExcepcionesTecnicas.BAD_REQUEST)) );
    }

    public Mono<ServerResponse> buscarBarberiaPorNombre(ServerRequest request){
        return buscarBarberiaUC.buscarPorNombre(request.pathVariable("nombre"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> buscarBarberiaPorRazonSocial(ServerRequest request){
        return buscarBarberiaUC.buscarPorRazonSocial(request.pathVariable("razonSocial"))
                .map(mapper::toResponse)
                .collectList()
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> buscarBarberiaPorDocumento(ServerRequest request){
        return buscarBarberiaUC.bucarPorDocumento(request.pathVariable("documento"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> buscarBarberiaPorId(ServerRequest request){
        return buscarBarberiaUC.buscarPorId(request.pathVariable("id"))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> actualizarBarberia(ServerRequest request){
        return request.bodyToMono(BarberiaRqDto.class)
                .doOnNext(validacionRequest::validar)
                .map(mapper::toDomain)
                .flatMap(barberia -> actualizarBarberiaUC
                        .actualizarBarberia(request.pathVariable("id"), barberia))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.accepted().bodyValue(response));
    }
}
