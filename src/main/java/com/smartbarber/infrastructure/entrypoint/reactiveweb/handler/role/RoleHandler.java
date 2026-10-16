package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.role;

import com.smartbarber.application.usecase.role.CreateRoleUC;
import com.smartbarber.application.usecase.role.RoleUpdateUC;
import com.smartbarber.application.usecase.role.SearchRoleUC;
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
    private final RoleUpdateUC roleUpdateUC;

    public Mono<ServerResponse> createRole(ServerRequest request) {
        return request.bodyToMono(RoleRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toCommand)
                .flatMap(createRoleUC::createRole)
                .map(mapper::toResponse)
                .flatMap(response ->
                        ServerResponse.created(request.uri()).bodyValue(response)
                )
                .switchIfEmpty(Mono.error(
                        new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)
                ));
    }

    public Mono<ServerResponse> findRoleById(ServerRequest request) {
        return searchRoleUC.findById(Short.valueOf(request.pathVariable("id")))
                .map(mapper::toResponse)
                .flatMap(response -> ServerResponse.ok().bodyValue(response));
    }

    public Mono<ServerResponse> updateRole(ServerRequest request) {
        return request.bodyToMono(RoleRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toCommand)
                .flatMap(command ->
                        roleUpdateUC.updateRole(
                                Short.valueOf(request.pathVariable("id")),
                                command
                        )
                )
                .map(mapper::toResponse)
                .flatMap(response ->
                        ServerResponse.accepted().bodyValue(response)
                );
    }
}
