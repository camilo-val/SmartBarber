package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.user;

import com.smartbarber.application.usecase.user.CreateUserUC;
import com.smartbarber.application.usecase.user.SearchUserUC;
import com.smartbarber.application.usecase.user.UpdateUserUC;
import com.smartbarber.domain.port.FirebaseAuthPort;
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
    private final FirebaseAuthPort firebaseAuthPort;

    public Mono<ServerResponse> createUser(ServerRequest request) {

        log.info("Creating user request");

        String authorization = request.headers()
                .firstHeader("Authorization");

        log.info("Authorization header recibido: {}",
                authorization != null ? "SI" : "NO");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            log.error("Authorization Bearer no recibido correctamente");

            return Mono.error(
                    new TechnicalExceptions(
                            TechnicalMessageExceptions.BAD_REQUEST
                    )
            );
        }

        String idToken = authorization.substring(7);

        log.info("Token Firebase recibido. Longitud: {}", idToken.length());

        return firebaseAuthPort.verifyToken(idToken)
                .doOnNext(firebaseId ->
                        log.info("Firebase UID verificado correctamente")
                )
                .flatMap(firebaseId ->
                        request.bodyToMono(UserRqDto.class)
                                .doOnNext(body ->
                                        log.info("Body recibido: roleId={}", body.roleId())
                                )
                                .doOnNext(validateRequest::validate)
                                .map(userRqDto ->
                                        mapper.toDomain(userRqDto, firebaseId)
                                )
                )
                .doOnNext(user ->
                        log.info("Usuario de dominio creado. RoleId={}",
                                user.getRoleId())
                )
                .flatMap(createUserUC::crearUsuario)
                .doOnNext(user ->
                        log.info("Usuario creado correctamente. ID={}",
                                user.getId())
                )
                .map(mapper::toResponse)
                .flatMap(response ->
                        ServerResponse.created(request.uri())
                                .bodyValue(response)
                )
                .switchIfEmpty(
                        Mono.error(
                                new TechnicalExceptions(
                                        TechnicalMessageExceptions.BAD_REQUEST
                                )
                        )
                )
                .doOnError(error ->
                        log.error("Error creating user", error)
                );
    }

    public Mono<ServerResponse> SearchUserId(ServerRequest request) {

        return searchUserUC.buscarPorId(request.pathVariable("id"))
                .map(mapper::toResponse)
                .flatMap(response ->
                        ServerResponse.ok().bodyValue(response)
                )
                .switchIfEmpty(
                        ServerResponse.notFound().build()
                );
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {

        return request.bodyToMono(UserRqDto.class)
                .doOnNext(validateRequest::validate)
                .map(mapper::toDomainForUpdate)
                .flatMap(user ->
                        updateUserUC.userUpdate(
                                request.pathVariable("id"),
                                user
                        )
                )
                .map(mapper::toResponse)
                .flatMap(response ->
                        ServerResponse.accepted().bodyValue(response)
                );
    }
}