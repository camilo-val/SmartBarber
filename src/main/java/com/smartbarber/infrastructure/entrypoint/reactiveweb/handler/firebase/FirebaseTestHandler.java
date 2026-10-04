package com.smartbarber.infrastructure.entrypoint.reactiveweb.handler.firebase;

import com.smartbarber.domain.port.FirebaseAuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class FirebaseTestHandler {

    private final FirebaseAuthPort firebaseAuthPort;

    public Mono<ServerResponse> verifyToken(ServerRequest request) {

        String authorization = request.headers()
                .firstHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return ServerResponse.badRequest()
                    .bodyValue("Authorization Bearer token is required");
        }

        String idToken = authorization.substring(7);

        return firebaseAuthPort.verifyToken(idToken)
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