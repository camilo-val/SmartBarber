package com.smartbarber.domain.port;

import com.smartbarber.domain.model.user.User;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UserPort {
    Mono<User> buscarUsuarioPorId(UUID id);
    Mono<User> crearUsuario(User user);
    Mono<User> actualizarUsuario(UUID id, User user);
    Mono<Void> eliminarUsuario(UUID id);
    Mono<Boolean> existeUsuarioPorFirebaseId(String firebaseId);
}
