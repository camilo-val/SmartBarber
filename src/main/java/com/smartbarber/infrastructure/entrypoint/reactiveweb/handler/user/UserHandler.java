package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.user;

import com.smartbarber.application.usecase.user.UpdateUserUC;
import com.smartbarber.application.usecase.user.SearchUserUC;
import com.smartbarber.application.usecase.user.CreateUserUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.dto.User.UserRqDto;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.exception.TechnicalMessageExceptions;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.user.UserEntryMapper;
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
public class UserHandler {
    private final UserEntryMapper mapper;
    private final CreateUserUC createUserUC;
    private final validateRequest validateRequest;
    private final SearchUserUC searchUserUC;
    private final UpdateUserUC updateUserUC;

    public Mono<ServerResponse> createUser(ServerRequest request){
        return request.bodyToMono(UserRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap(createUserUC::crearUsuario)
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.created(request.uri()).bodyValue(response))
                .switchIfEmpty(Mono.error(new TechnicalExceptions(TechnicalMessageExceptions.BAD_REQUEST)));
    }

    public Mono<ServerResponse> SearchUserId(ServerRequest request) {
        return searchUserUC.buscarPorId(request.pathVariable("id"))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.ok().bodyValue(response))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        return request.bodyToMono(UserRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomain)
                .flatMap( user -> updateUserUC
                        .actualizarUsuario(request.pathVariable("id"), user))
                .map(mapper::toResponse)
                .flatMap( response -> ServerResponse.accepted().bodyValue(response));
    }
}
