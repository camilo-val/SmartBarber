package com.smartbarber.application.usecase.user;

import com.smartbarber.domain.exceptions.BusinessExceptions;
import com.smartbarber.domain.exceptions.user.UserMessageExceptions;
import com.smartbarber.domain.model.user.User;
import com.smartbarber.domain.port.RolePort;
import com.smartbarber.domain.port.UserPort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateUserUC {

    private final UserPort userPort;
    private final RolePort rolePort;

    public Mono<User> crearUsuario(User user) {
        return userPort.existByFirebaseId(user.getFirebaseId())
                .flatMap(exists -> {

                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(() -> new BusinessExceptions(
                                UserMessageExceptions.USUARIO_EXISTENTE
                        ));
                    }

                    return rolePort.findById(user.getRoleId())
                            .switchIfEmpty(Mono.error(() -> new BusinessExceptions(
                                    UserMessageExceptions.ROL_NO_EXISTE
                            )))
                            .thenReturn(user);
                })
                .flatMap(userPort::save);
    }
}