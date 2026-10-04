package com.smartbarber.domain.port;

import reactor.core.publisher.Mono;

public interface FirebaseAuthPort {

    Mono<String> verifyToken(String idToken);
    Mono<Boolean> isExpiredToken(String idToken);
}
