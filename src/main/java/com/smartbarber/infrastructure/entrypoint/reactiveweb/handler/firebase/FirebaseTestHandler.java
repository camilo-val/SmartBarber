package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.firebase;

import com.smartbarber.application.usecase.athentication.Authentication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FirebaseTestHandler {

    private final Authentication authentication;

    public Mono<ServerResponse> verifyToken(ServerRequest request) {

        String authorization = request.headers()
                .firstHeader(HttpHeaders.AUTHORIZATION);

        return authentication.authorization(authorization)
                .flatMap(uid -> ServerResponse.ok()
                        .bodyValue(uid))
               /* .onErrorResume(error ->
                        ServerResponse.status(401)
                                .bodyValue("Invalid Firebase ID token"))*/
                .onErrorResume(errorr -> {
                    errorr.printStackTrace();
                    return Mono.error(() -> new RuntimeException("Invalid token"));});
    }
}