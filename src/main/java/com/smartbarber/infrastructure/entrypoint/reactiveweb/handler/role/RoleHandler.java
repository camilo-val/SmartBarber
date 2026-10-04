package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.role;

import com.smartbarber.application.usecase.role.CreateRoleUC;
import com.smartbarber.application.usecase.role.SearchRoleUC;
import com.smartbarber.application.usecase.role.RoleUpdateUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.role.RoleRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.role.RoleEntryMapper;
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
public class RoleHandler {
    private final RoleEntryMapper mapper;
    private final CreateRoleUC createRoleUC;
    private final validateRequest validateRequest;
    private final SearchRoleUC searchRoleUC;
    private final RoleUpdateUC RoleUpdateUC;

    public Mono<ServerResponse> createRole(ServerRequest request){
        return request.bodyToMono(RoleRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(createRoleUC::crearRol)
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)) );
    }

    public Mono<ServerResponse> findRoleById(ServerRequest request){
        return searchRoleUC.buscarPorId(Short.valueOf(request.pathVariable("id")))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateRole(ServerRequest request){
        return request.bodyToMono(RoleRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(role -> RoleUpdateUC
                        .roleUpdate(Short.valueOf(request.pathVariable("id")), role))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.accepted().bodyValue(response));

    }
}
