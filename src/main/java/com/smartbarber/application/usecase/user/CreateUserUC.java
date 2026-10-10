package com.smartbarber.application.usecase.user;

import com.smartbarber.application.command.in.user.UserCommand;
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

    public Mono<User> crearUsuario(UserCommand user) {
        return userPort.existByFirebaseId(user.firebaseId())
                .flatMap(exists -> {

                    if (Boolean.TRUE.equals(exists)) {
                        return Mono.error(() -> new BusinessExceptions(
                                UserMessageExceptions.USUARIO_EXISTENTE
                        ));
                    }

                    return rolePort.findById(user.roleId())
                            .switchIfEmpty(Mono.error(() -> new BusinessExceptions(
                                    UserMessageExceptions.ROL_NO_EXISTE
                            )))
                            .thenReturn(User.createUserString(user.firebaseId(), user.roleId()));
                })
                .flatMap(userPort::save);
    }
}