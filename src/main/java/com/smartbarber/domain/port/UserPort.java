package com.smartbarber.domain.port;

import com.smartbarber.domain.model.user.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserPort {
    Mono<User> findById(UUID id);
    Mono<User> save(User user);
    Mono<User> update(UUID id, User user);
    Mono<Void> eliminarUsuario(UUID id);
    Mono<Boolean> existByFirebaseId(String firebaseId);
}
