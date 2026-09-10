package com.smartbarber.application.usecase.user;

import com.smartbarber.application.port.UserPort;
import com.smartbarber.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UpdateUserUC {
    private final UserPort userPort;

    public Mono<User> actualizarUsuario(String id, User user){

        return userPort.buscarUsuarioPorId(UUID.fromString(id))
                .map( userPort -> User.actualizar(userPort.getId(), user.getFirebaseId() ,user.getEstado(), userPort.getFechaCreacion(), LocalDate.now()))
                .flatMap(userModificado -> userPort.actualizarUsuario(UUID.fromString(id), userModificado));
    }
}
