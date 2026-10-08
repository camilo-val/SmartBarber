package com.smartbarber.domain.port;

import reactor.core.publisher.Mono;

public interface FirebaseAuthPort {

    Mono<Boolean> isValidToken(String idToken);
    Mono<Boolean> isExpiredToken(String idToken);
    Mono<String> getUid(String idToken);
}
