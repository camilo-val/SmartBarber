package com.smartbarber.application.usecase;

import com.smartbarber.application.port.UserPort;
import com.smartbarber.domain.exceptions.UserExceptions;
import com.smartbarber.domain.exceptions.MessageExceptionUser;
import com.smartbarber.domain.model.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CreateUserUC {
    private final UserPort userPort;

    public Mono<User> crearUsuario(User user) {
        return userPort.existeUsuarioPorFirebaseId(user.getFirebaseId())
                .flatMap( exist-> {
                    if (Boolean.TRUE.equals(exist)){
                        Mono.error(new UserExceptions(MessageExceptionUser.USUARIO_EXISTENTE));
                    }
                    return Mono.just(user);
                })
                .flatMap(userPort::crearUsuario);
    }
}
