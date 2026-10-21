package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.firebase;

import com.smartbarber.application.usecase.athentication.AuthenticationUC;
import com.smartbarber.infrastructure.entrypoint.reactiveweb.mapper.authorization.AuthorizationEntryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FirebaseTestHandler {

    private final AuthenticationUC authentication;
    private final AuthorizationEntryMapper authorizationEntryMapper;

    public Mono<ServerResponse> verifyToken(ServerRequest request) {

        String authorization = request.headers()
                .firstHeader(HttpHeaders.AUTHORIZATION);

        return authentication.authorizationSession(authorization)
                .map(authorizationEntryMapper::toResponse)
                .flatMap(response -> ServerResponse.ok()
                        .bodyValue(response))
               /* .onErrorResume(error ->
                        ServerResponse.status(401)
                                .bodyValue("Invalid Firebase ID token"))*/;
    }
}